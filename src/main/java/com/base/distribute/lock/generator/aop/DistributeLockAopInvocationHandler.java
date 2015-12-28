package com.base.distribute.lock.generator.aop;


/**
 * 
 * @author yubing
 *
 */

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.base.distribute.lock.generator.constant.DLGConstants;
import com.base.distribute.lock.generator.core.RedisDistributeLockImpl;
import com.base.distribute.lock.generator.core.ZookeeperDistributeLockImpl;
import com.base.distribute.lock.generator.dbmodule.pojo.DistributeLockTypeValueInDB;
import com.base.distribute.lock.generator.dbmodule.pojo.OwnDistributeLockStatus;
import com.base.distribute.lock.generator.util.IPUtil;

public class DistributeLockAopInvocationHandler implements InvocationHandler {
	
	private static Logger logger = LoggerFactory.getLogger(DistributeLockAopInvocationHandler.class);

	private Object proxyTarget;
	
	private AbstractDistributeLockAopEntity proxyDistributeLockAopEntity;
	
	public DistributeLockAopInvocationHandler(Object target,AbstractDistributeLockAopEntity proxyDistributeLockAopEntity){
		proxyTarget = target;
		this.proxyDistributeLockAopEntity = proxyDistributeLockAopEntity;
	}
	
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		  Method originalMethod = proxyTarget.getClass().getMethod(method.getName(), method.getParameterTypes());
		  if (!originalMethod.isAnnotationPresent(DistributeLockAopTag.class)) {
		      return method.invoke(proxyTarget, args);
		  }
		  Object result = method.invoke(proxyTarget, args);
		  Map<String,Object> params = generateParamsFromTargetObject(originalMethod.getName(),result);
		  proxyDistributeLockAopEntity.logDistributeLockInfo(params);
		  return result;			
	}
	
		private Map<String,Object> generateParamsFromTargetObject(String originTargetMethodName,Object result){
			
			logger.info("generateParamsFromTargetObject,target:{},targetMethodName:{}",proxyTarget.getClass(),originTargetMethodName);
			
			if(proxyTarget instanceof RedisDistributeLockImpl){
				Map<String,Object> params = new HashMap<String,Object>();
				params.put(DLGConstants.CALL_METHOD_NAME, originTargetMethodName);
				RedisDistributeLockImpl redisDistributeLockImpl = (RedisDistributeLockImpl)proxyTarget;
				if(redisDistributeLockImpl.getCurrentRedisLockEntity() != null){
					params.put(DLGConstants.LOCK_CLIENT_UUID, redisDistributeLockImpl.getCurrentRedisLockEntity().getUuid());
					params.put(DLGConstants.LOCK_CRITICAL_KEY,redisDistributeLockImpl.getCurrentRedisLockEntity().getLockKey());
				}
				
				if(redisDistributeLockImpl.getRedisConnection() != null){
					params.put(DLGConstants.APP_USE_DISTRIBUTE_LOCK, redisDistributeLockImpl.getRedisConnection().getRedisConfigInfo().getAppName());
				}
				
				if(result instanceof Boolean){
					
					Boolean resultBooleanValue = (Boolean)result;
					
					if(resultBooleanValue != null && resultBooleanValue == true){
						params.put(DLGConstants.ISOWN_DISTRIBUTE_LOCK, OwnDistributeLockStatus.LOCKED.getValue());
						if("acquireLock".equals(originTargetMethodName) || "tryAcquireLock".equals(originTargetMethodName)){
							params.put(DLGConstants.ACQUIRE_LOCK_TIME, new Date());
						}
						
						if("releaseLock".equals(originTargetMethodName)){
							params.put(DLGConstants.RELEASE_LOCK_TIME, new Date());
							params.put(DLGConstants.ISOWN_DISTRIBUTE_LOCK, OwnDistributeLockStatus.UNLOCKED.getValue());
						}
						
					}
				}
				
				params.put(DLGConstants.CLIENT_IP, IPUtil.getLocalNetWorkIp());
				params.put(DLGConstants.LOCK_TYPE, DistributeLockTypeValueInDB.REDIS_VALUE_IN_DB.getValue());
				params.put(DLGConstants.REQUEST_LOCK_TIME, new Date());
				
				return params;
				
				
			} else if(proxyTarget instanceof ZookeeperDistributeLockImpl){
				Map<String,Object> params = new HashMap<String,Object>();
				params.put(DLGConstants.CALL_METHOD_NAME, originTargetMethodName);
				ZookeeperDistributeLockImpl zookeeperDistributeLockImpl = (ZookeeperDistributeLockImpl)proxyTarget;
				if(zookeeperDistributeLockImpl.getCurrentZookeeperNodeInfo() != null){
					params.put(DLGConstants.LOCK_CLIENT_UUID, zookeeperDistributeLockImpl.getCurrentZookeeperNodeInfo().getUuid());
					params.put(DLGConstants.LOCK_CRITICAL_KEY,zookeeperDistributeLockImpl.getCurrentZookeeperNodeInfo().getZkNodeId());
				}
				
				if(zookeeperDistributeLockImpl.getZookeeperConnection() != null){
					params.put(DLGConstants.APP_USE_DISTRIBUTE_LOCK, zookeeperDistributeLockImpl.getZookeeperConnection().getZookeeperConfigInfo().getAppName());
				}
				
				if(result instanceof Boolean){
					Boolean resultBooleanValue = (Boolean)result;
					if(resultBooleanValue != null && resultBooleanValue == true){
						params.put(DLGConstants.ISOWN_DISTRIBUTE_LOCK, OwnDistributeLockStatus.LOCKED.getValue());
						if("acquireLock".equals(originTargetMethodName) || "tryAcquireLock".equals(originTargetMethodName)){
							params.put(DLGConstants.ACQUIRE_LOCK_TIME, new Date());
						}
						
						if("releaseLock".equals(originTargetMethodName)){
							params.put(DLGConstants.ISOWN_DISTRIBUTE_LOCK, OwnDistributeLockStatus.UNLOCKED.getValue());
							params.put(DLGConstants.RELEASE_LOCK_TIME, new Date());
						}
						
					}
				}
				
				params.put(DLGConstants.CLIENT_IP, IPUtil.getLocalNetWorkIp());
				params.put(DLGConstants.LOCK_TYPE, DistributeLockTypeValueInDB.ZOOKEEPER_VALUE_IN_DB.getValue());
				params.put(DLGConstants.REQUEST_LOCK_TIME, new Date());
				return params;
			}
			return null;
		}

}
