package com.base.distribute.lock.generator.zookeeper;

import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author yubing
 *
 */
public class ZookeeperDistributeLockServiceImpl implements
		ZookeeperDistributeLockService {
	
	
	private static Logger logger = LoggerFactory.getLogger(ZookeeperDistributeLockServiceImpl.class);
	
	
	private  ZooKeeper zooKeeper;
	
	public ZookeeperDistributeLockServiceImpl(ZooKeeper zookeeper){
		this.zooKeeper = zookeeper;
	}
	

	public void deleteLockPath(String lockPath) throws Exception {
		logger.info("deleteLockPath----lockPath:{}",lockPath);
		zooKeeper.delete(lockPath, -1);
		
	}

	public  void close() throws Exception {
			if (zooKeeper != null) {
				try {
					zooKeeper.close();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					logger.error("执行关闭zookeeper链接出现错误");
				} finally {
					zooKeeper = null;
				}
			}
		

	}

	public String createLockPath(String lockPath,CreateMode flags) throws Exception {
		logger.info("createLockPath----lockPath:{}",lockPath);
		return zooKeeper.create(lockPath, null, Ids.OPEN_ACL_UNSAFE,flags);
	}

	public List<String> getChildren(String path, Watcher watcher)
			throws Exception {
		
		return zooKeeper.getChildren(path, watcher);
	}


	public boolean existPath(String path, boolean watch) throws Exception {
		
		return zooKeeper.exists(path, watch) != null ? true : false;
	}

}
