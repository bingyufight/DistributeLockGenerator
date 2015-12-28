package com.base.distribute.lock.generator.zookeeper;

import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;


/**
 * 
 * @author yubing
 *
 */

public interface ZookeeperDistributeLockService {
	
	boolean existPath(String path,boolean watch) throws Exception;
	
	void deleteLockPath(String lockPath) throws Exception;
	
	void close() throws Exception;
	
    String createLockPath(String lockPath,CreateMode flags) throws Exception;
	
	List<String> getChildren(final String path, Watcher watcher) throws Exception;
	


}
