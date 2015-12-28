package com.base.distribute.lock.generator.zookeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.base.distribute.lock.generator.constant.DLGConstants;
import com.base.distribute.lock.generator.core.DistributeLockType;
import com.base.distribute.lock.generator.exception.DistributeLockException;
import com.base.distribute.lock.generator.util.ConfigUtil;

/**
 * 
 * @author yubing
 *
 */

public class ZookeeperConnection {
	
	private static final Logger logger = LoggerFactory.getLogger(ZookeeperConnection.class);
	
	private ZooKeeper zooKeeper;
	
	private ZookeeperConfigInfo zookeeperConfigInfo;
	
	private ZookeeperDistributeLockService zookeeperDistributeLockService;
	
	public ZookeeperConnection(){
		loadZookeeperConfigInfo();
	}
	
	
	private void loadZookeeperConfigInfo(){
		zookeeperConfigInfo = new ZookeeperConfigInfo();
		zookeeperConfigInfo.setZookeeperAddress(ConfigUtil.getProperty(DistributeLockType.ZOOKEEPER.getValue(),"zookeeper.server.address", DLGConstants.DEFAULT_ZOOKEEPER_ADDRESS));
		zookeeperConfigInfo.setAppName(ConfigUtil.getProperty(DistributeLockType.ZOOKEEPER.getValue(),"app.use.zookeeper.distribute.lock", DLGConstants.DEFAULT_APP_NAME));
		zookeeperConfigInfo.setLockPath(ConfigUtil.getProperty(DistributeLockType.ZOOKEEPER.getValue(),"zookeeper.lock.path",DLGConstants.DEFAULT_LOCK_PATH));
		zookeeperConfigInfo.setTimeOut(Integer.parseInt(ConfigUtil.getProperty(DistributeLockType.ZOOKEEPER.getValue(),"zookeeper.timeout",DLGConstants.DEFAULT_TIME_OUT_TIME)));
		
		logger.info("zookeeperConfigInfo=" + zookeeperConfigInfo.toString());	
	}
	
	
	public synchronized ZooKeeper getConnectedZooKeeper() throws DistributeLockException, InterruptedException{
		logger.info("Start to execute getConnectedZooKeeper------,threadName:{}",Thread.currentThread().getName());
		 if (this.zooKeeper == null || !zooKeeper.getState().isAlive()) {
	            final CountDownLatch connectCountdown = new CountDownLatch(1);
	            try {
	                this.zooKeeper = new ZooKeeper(zookeeperConfigInfo.getZookeeperAddress(), zookeeperConfigInfo.getTimeOut(), new Watcher() {

	                    public void process(WatchedEvent event) {
	                    	logger.info("zookeeper connect event , {}", event.toString());
	                        if (event.getType() == Event.EventType.None && event.getPath() == null) { //connect event
	                            if (Event.KeeperState.SyncConnected == event.getState()) {
	                            	connectCountdown.countDown();
	                            	logger.info("zookeeper connected");
	                            } else if (Event.KeeperState.Disconnected == event.getState()) {
	                            	logger.info("zookeeper disconnected");
	                            } else if (Event.KeeperState.Expired == event.getState()) {
	                                logger.info("Session is expired, need to clear process");
	                            }
	                        }
	                    }
	                });
	            } catch (IOException e) {
	                throw new DistributeLockException("connect zookeeper error", e);
	            }
	            connectCountdown.await();
	        }

	        return this.zooKeeper;
	}
	
	
	public synchronized ZookeeperDistributeLockService getConnectedZooKeeperDistributeLockService() throws DistributeLockException, InterruptedException{
		
		if(zookeeperDistributeLockService == null){
			 zookeeperDistributeLockService = new ZookeeperDistributeLockServiceImpl(getConnectedZooKeeper());
		}
		return zookeeperDistributeLockService; 
	}


	public ZookeeperConfigInfo getZookeeperConfigInfo() {
		return zookeeperConfigInfo;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	private static class ZkConnectionWatcher implements Watcher {
//        @Override
//        public void process(WatchedEvent event) {
//            if (event.getType() == Event.EventType.NodeCreated) {
//                logger.debug("zk分布式锁:节点创建:{}", event.getPath());
//            } else if (event.getState() == Event.KeeperState.SyncConnected) {
//                logger.debug("zk分布式锁:zookeeper连接成功, thread:{}, session:{}", Thread.currentThread().getName(),
//                        Long.toHexString(zookeeper.getSessionId()));
//            }
//        }
//    }
//	
	

}
