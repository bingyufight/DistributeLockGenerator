package com.base.distribute.lock.generator.core;

import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.base.distribute.lock.generator.aop.DistributeLockAopTag;
import com.base.distribute.lock.generator.aop.LogInDBDistributeLockAopEntity;
import com.base.distribute.lock.generator.exception.DistributeLockException;
import com.base.distribute.lock.generator.redis.RedisConnection;
import com.base.distribute.lock.generator.redis.RedisLockEntity;

/**
 * 
 * @author yubing
 *
 */
public class RedisDistributeLockImpl implements IDistributeLock {

	private static final Logger logger = LoggerFactory.getLogger(RedisDistributeLockImpl.class);
	
	private final RedisConnection redisConnection;
	
	private  RedisLockEntity currentRedisLockEntity;
	
    private volatile boolean currentRedisEntityHasLocked;
    
    private static final ReentrantLock lock = new ReentrantLock();
	
	public RedisDistributeLockImpl(){
		this.redisConnection = new RedisConnection();
		String lockKey = redisConnection.getRedisConfigInfo().getAppName() + "_" + redisConnection.getRedisConfigInfo().getLockSeparatedKey();
		long lockTimeOut = redisConnection.getRedisConfigInfo().getLockTimeOut();
		currentRedisLockEntity = new RedisLockEntity();
		currentRedisLockEntity.setLockKey(lockKey);
		currentRedisLockEntity.setLockTimeout(lockTimeOut);
		
	}
	
	@DistributeLockAopTag(defaultAopHandleClass =LogInDBDistributeLockAopEntity.class)
	public boolean acquireLock() throws DistributeLockException {
		logger.info("start to  execute RedisDistributeLockImpl acquireLock-------,threadName:{}",Thread.currentThread().getName());
		
		lock.lock();
		
		try{
				if(currentRedisLockEntity == null){
					logger.info("currentRedisLockEntity can not initialize successfully.");
					currentRedisEntityHasLocked = false;
					return false;
				}
				
				if(isLocked()){
					return true;
				}
				
				long currentTimeMill = System.currentTimeMillis();
				String lockValue = String.valueOf(currentTimeMill + currentRedisLockEntity.getLockTimeout() + 1);
				if(redisConnection.getConnectionRedisDistributeLockService().setNx(currentRedisLockEntity.getLockKey(), lockValue) == 1){
					currentRedisEntityHasLocked = true;
					return true;
				}
				
				String currentLockValueInRedis = redisConnection.getConnectionRedisDistributeLockService().getValue(currentRedisLockEntity.getLockKey());
				if(currentLockValueInRedis != null){
					long currentLockValueInRedisLong = Long.parseLong(currentLockValueInRedis);
					if(currentLockValueInRedisLong < System.currentTimeMillis()){
						 String checkOldLockValue = redisConnection.getConnectionRedisDistributeLockService().getSetValue(currentRedisLockEntity.getLockKey(), lockValue);
		                    if (checkOldLockValue == null || checkOldLockValue.equals(currentLockValueInRedis)) {
		                    	currentRedisEntityHasLocked = true;
		                        return true;
		                    }
					}
				}	        
				
		}catch(Exception e){
			logger.error("execute acquireLock occur error", e);
			throw new DistributeLockException("execute acquireLock occur error",e);
		}finally{
			resetLockStatusAndReturnRedisResource(false);
			lock.unlock();
		}
		
		return false;
		
		
	}

	@DistributeLockAopTag(defaultAopHandleClass =LogInDBDistributeLockAopEntity.class)
	public boolean releaseLock() throws DistributeLockException {
		logger.info("start to execute RedisDistributeLockImpl releaseLock-------,threadName:{}",Thread.currentThread().getName());
		lock.lock();
		try{
			  if(isLocked()){
					return redisConnection.getConnectionRedisDistributeLockService().delKey(currentRedisLockEntity.getLockKey()) == 1;         
			  } 
		}catch(Exception e){
			logger.error("delete redis lock occur error", e);
			throw new DistributeLockException("execute releaseLock occur error",e);
		}finally{
			resetLockStatusAndReturnRedisResource(true);
			lock.unlock();
		}
		return false;
	}

	public boolean isLocked() throws DistributeLockException{
		logger.info("start to execute RedisDistributeLockImpl isLocked-------,threadName:{}",Thread.currentThread().getName());
		try{
				if(currentRedisLockEntity == null || currentRedisEntityHasLocked == false){
					return false;
				}
				
				if(currentRedisEntityHasLocked){
					String lockValue = redisConnection.getConnectionRedisDistributeLockService().getValue(currentRedisLockEntity.getLockKey());
					long lockValueLong = 0L;
					if(lockValue != null){
						lockValueLong = Long.parseLong(lockValue);
						long currentTimeValue = System.currentTimeMillis();
						if(currentTimeValue > lockValueLong){
							logger.info("the redis distribucte has expired");
							return false;
						} else {
							return true;
						}
						
					}
				}
		}catch(Exception e){
			logger.error("exec isLocked() occur unknown error",e);
			throw new DistributeLockException("isLocked() execute occur error");
		}finally{
			try {
				redisConnection.getConnectionRedisDistributeLockService().returnJedisResource();
			} catch (Exception e) { 
				e.printStackTrace();
			}		
			
		}
		return false;
	}

	@DistributeLockAopTag(defaultAopHandleClass =LogInDBDistributeLockAopEntity.class)
	public boolean tryAcquireLock(long duration) throws DistributeLockException {
		logger.info("start to execute RedisDistributeLockImpl tryAcquireLock-------,threadName:{}",Thread.currentThread().getName());
		lock.lock();
		try{
			if(isLocked()){
				throw new DistributeLockException("already own the redis lock");
			}
			
			 acquireLock();
			 if(!currentRedisEntityHasLocked){
				//first time not lock,wait duration again to lock.
				Thread.sleep(duration);
				return acquireLock();
			 }
						    	
		}catch(Exception e){
			logger.error("execute tryAcquireLock occur error", e);
			throw new DistributeLockException("execute tryAcquireLock occur error",e);
		}finally{
			resetLockStatusAndReturnRedisResource(false);
			lock.unlock();
		}
		
		return false;
	}
	
	
	private void resetLockStatusAndReturnRedisResource(boolean resetLockedStatus){
		logger.info("begin to execute RedisDistributeLockImpl returnRedisResource--------,threadName:{}",Thread.currentThread().getName());
		try{
			if(currentRedisLockEntity != null){
				redisConnection.getConnectionRedisDistributeLockService().returnJedisResource();
			}
		}catch(Exception e){
			logger.error("returnRedisResource occur error",e);
		}
		
		if(resetLockedStatus){
			currentRedisEntityHasLocked = false;
		}
		
		
		logger.info("returnRedisResource done --------");
	}

	public RedisLockEntity getCurrentRedisLockEntity() {
		return currentRedisLockEntity;
	}

	public RedisConnection getRedisConnection() {
		return redisConnection;
	}
	
	
	
	

	
}
