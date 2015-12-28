package com.base.distribute.lock.generator.core;



/**
 * 
 * @author yubing
 *
 */
public class DistributeLockManager {
	
	public static IDistributeLock createDistributeLock(DistributeLockType distributeLockType){
		if(DistributeLockType.ZOOKEEPER == distributeLockType){
			return DistributeLockProxyFactory.getDistributeLockInstance(ZookeeperDistributeLockImpl.class);
		} else {
			return DistributeLockProxyFactory.getDistributeLockInstance(RedisDistributeLockImpl.class);
		}
	}

}
