package com.base.distribute.lock.generator.core;

/**
 * 
 * @author yubing
 *
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.base.distribute.lock.generator.aop.DistributeLockAopTag;
import com.base.distribute.lock.generator.aop.LogInDBDistributeLockAopEntity;
import com.base.distribute.lock.generator.exception.DistributeLockException;
import com.base.distribute.lock.generator.zookeeper.ZookeeperConnection;
import com.base.distribute.lock.generator.zookeeper.ZookeeperDistributeLockService;
import com.base.distribute.lock.generator.zookeeper.ZookeeperNodeInfo;

public class ZookeeperDistributeLockImpl implements IDistributeLock {
	
	
	private static final Logger logger = LoggerFactory.getLogger(ZookeeperDistributeLockImpl.class);
	
	private  static final String lockStartFlag= "/dlock_";
	
	private final ZookeeperConnection zookeeperConnection;
	
	private ZookeeperNodeInfo currentZookeeperNodeInfo;
	
	private String fullLockPathParent;
	
	private volatile boolean currentZookeeperNodeHasLocked;
	
	 private static final ReentrantLock lock = new ReentrantLock();
	 
	 private String configLockPath; //common lock path
	
	
	public ZookeeperDistributeLockImpl(){
		
		this.zookeeperConnection =new ZookeeperConnection();
		configLockPath = this.zookeeperConnection.getZookeeperConfigInfo().getLockPath();
		if (configLockPath.lastIndexOf('/') > 0){  // remove the "/" at the end of string
			configLockPath = configLockPath.substring(0, configLockPath.lastIndexOf('/'));
		}
		
		String configAppName = this.zookeeperConnection.getZookeeperConfigInfo().getAppName();
		configAppName = configAppName.replaceAll("/", "");  //replace the invalid chararter / to empty
			
		fullLockPathParent = configLockPath + "/" + configAppName;
	}
	
	

	@DistributeLockAopTag(defaultAopHandleClass =LogInDBDistributeLockAopEntity.class)
	public synchronized boolean acquireLock() throws DistributeLockException {
		logger.info("start to execute ZookeeperDistributeLockImpl  acquireLock-------,threadName:{}",Thread.currentThread().getName());
	//	System.out.println("start to execute ZookeeperDistributeLockImpl  acquireLock-------,threadName:{}"+Thread.currentThread().getName());
		lock.lock();
		try{
			if(isLocked()){
				return true;
			}
			
			initLockEnviroment();
			doInnerLock();
			if(currentZookeeperNodeHasLocked){
				return true;
			}	
				
		}catch(Exception e){
			logger.error("execute acquireLock occur error", e);
			throw new DistributeLockException("execute acquireLock occur error",e);
		}finally{
			lock.unlock();
		}
	
		return false;
	}

	@DistributeLockAopTag(defaultAopHandleClass =LogInDBDistributeLockAopEntity.class)
	public  boolean releaseLock() throws DistributeLockException {
		logger.info("start to execute ZookeeperDistributeLockImpl  releaseLock-------,threadName:{}",Thread.currentThread().getName());
		System.out.println("start to execute ZookeeperDistributeLockImpl  releaseLock-------,threadName:{}"+Thread.currentThread().getName());
		lock.lock();
		try{
		
			if(isLocked()){
				currentZookeeperNodeHasLocked = false;
				cleanAndRemoveUnsedZnode();
				return true;
			}
		}catch(Exception e){
			logger.error("execute releaseLock occur error", e);
			throw new DistributeLockException("execute releaseLock occur error",e);
		}finally{
			lock.unlock();
		}
		return false;
	}

	public  boolean isLocked() throws DistributeLockException {
		logger.info("start to execute ZookeeperDistributeLockImpl  isLocked-------,threadName:{}",Thread.currentThread().getName());
		
		try{
			 if(currentZookeeperNodeInfo == null) {
		            return false;
		      }

			 TreeSet<ZookeeperNodeInfo> zookeeperNodeInfoSet = getZookeeperNodeInfos();
	            if (zookeeperNodeInfoSet != null && zookeeperNodeInfoSet.size() > 0) {
	            	ZookeeperNodeInfo firstZookeeperNode = zookeeperNodeInfoSet.first();
	                if (firstZookeeperNode != null && firstZookeeperNode.equals(currentZookeeperNodeInfo)) {
	                    return true;
	                }
	            }
		}catch(Exception e){
			logger.error("isLocked() execute occur error", e);
            throw new DistributeLockException("isLocked() execute occur error");
		}finally{
			
		}
	    return false;
	}

	@DistributeLockAopTag(defaultAopHandleClass =LogInDBDistributeLockAopEntity.class)
	public  boolean tryAcquireLock(long duration) throws DistributeLockException {
		logger.info("start to execute ZookeeperDistributeLockImpl  tryAcquireLock-------,threadName:{}",Thread.currentThread().getName());
		System.out.println("start to execute ZookeeperDistributeLockImpl  tryAcquireLock-------,threadName:{}"+Thread.currentThread().getName());
		lock.lock();
		try{
			if(isLocked()){
				//throw new DistributeLockException("already own the zookeeper lock");
				return true;
			}
			
			initLockEnviroment();
			doInnerLock();
			
			if(!currentZookeeperNodeHasLocked){
				//first time not lock,wait duration again to lock.
				 Thread.sleep(duration);
				 doInnerLock();
			}
			
			if(!currentZookeeperNodeHasLocked){
				return false;
			}
			
		}catch(Exception e){
			logger.error("execute acquireLock occur error", e);
			throw new DistributeLockException("execute acquireLock occur error",e);
		}finally{
			lock.unlock();
		}
		
		return true;
		
	}
	
	private void initLockEnviroment() throws TimeoutException, KeeperException,InterruptedException, IOException,Exception {
		
		if(currentZookeeperNodeInfo == null){
			
			if(!zookeeperConnection.getConnectedZooKeeperDistributeLockService().existPath(configLockPath, false)){ //first create the common zk distributeLockPath
				zookeeperConnection.getConnectedZooKeeperDistributeLockService().createLockPath(configLockPath,CreateMode.PERSISTENT);
			}
			
			if(!zookeeperConnection.getConnectedZooKeeperDistributeLockService().existPath(fullLockPathParent,false)){
				zookeeperConnection.getConnectedZooKeeperDistributeLockService().createLockPath(fullLockPathParent,CreateMode.PERSISTENT);
			}
			
			String currentZookeeperId = zookeeperConnection.getConnectedZooKeeperDistributeLockService().createLockPath(fullLockPathParent + lockStartFlag, CreateMode.EPHEMERAL_SEQUENTIAL);
			currentZookeeperNodeInfo = new ZookeeperNodeInfo(currentZookeeperId);	
		}
		
		
	}
	
	
	private TreeSet<ZookeeperNodeInfo> getZookeeperNodeInfos() throws KeeperException, InterruptedException,Exception {
        List<String> zookeeperNodeIds = zookeeperConnection.getConnectedZooKeeperDistributeLockService().getChildren(fullLockPathParent, null);
        logger.info("zookeeperNodeIds:{}",zookeeperNodeIds.toString());
        TreeSet<ZookeeperNodeInfo> zookeeperNodeInfoSet = new TreeSet<ZookeeperNodeInfo>();
        if(zookeeperNodeIds != null && zookeeperNodeIds.size() > 0){
        	 for (String zookeeperNodeId : zookeeperNodeIds) {
                 zookeeperNodeInfoSet.add(new ZookeeperNodeInfo(fullLockPathParent + "/" +zookeeperNodeId));
             }
        	  
        }
     
        return zookeeperNodeInfoSet;
    }

	
	private void cleanAndRemoveUnsedZnode(){
		logger.info("begin to execute cleanAndRemoveUnusedZnode---------");
		try{
			if(currentZookeeperNodeInfo != null){
				if(zookeeperConnection.getConnectedZooKeeperDistributeLockService().existPath(currentZookeeperNodeInfo.getZkNodeId(),false)){
					zookeeperConnection.getConnectedZooKeeperDistributeLockService().deleteLockPath(currentZookeeperNodeInfo.getZkNodeId());
				}
			}
		}catch(Exception e){
			logger.error("cleanAndRemoveUnusedZnode occur error",e);
		}
		
		
		
		logger.info("currentZookeeperNodeInfo done --------");
	}
	
	
	private void doInnerLock() throws DistributeLockException,Exception{
		logger.info("begin to execute doInnerLock----------");
		if(currentZookeeperNodeInfo == null){
			throw new DistributeLockException("request lock occur exception,current zookeeper node do not generate");
		}
		
		try{
			TreeSet<ZookeeperNodeInfo> zookeeperNodeInfoSet = getZookeeperNodeInfos();
			List<ZookeeperNodeInfo> zookeeperNodeInfoList = new ArrayList<ZookeeperNodeInfo>(zookeeperNodeInfoSet);
		    if(zookeeperNodeInfoList != null && zookeeperNodeInfoList.size() >0){
		    	for(int i=0;i < zookeeperNodeInfoList.size(); i++){
		    		if(currentZookeeperNodeInfo.equals(zookeeperNodeInfoList.get(i))){
		    			if(i==0){
		    				currentZookeeperNodeHasLocked = true;
		    			} else {
		    				detectPreZookeeperNodeInfo(zookeeperNodeInfoList.get(i-1));
		    			}
		    		}
		    	}
		    }
			
			
		} catch(Exception e){
			logger.error("execute doInnerLock occur error",e);
			
		}
	}
	
	private void detectPreZookeeperNodeInfo(final ZookeeperNodeInfo zookeeperNodeInfo) throws DistributeLockException,Exception{
		logger.info("begin to execute detectPreZookeeperNodeInfo---------");
		if(zookeeperNodeInfo == null){
			return ;
		}
		
		if(!zookeeperConnection.getConnectedZooKeeperDistributeLockService().existPath(zookeeperNodeInfo.getZkNodeId(),false)){
			doInnerLock();
		}
		
		
	}



	public ZookeeperNodeInfo getCurrentZookeeperNodeInfo() {
		return currentZookeeperNodeInfo;
	}



	public ZookeeperConnection getZookeeperConnection() {
		return zookeeperConnection;
	}

    
	
	
	

}
