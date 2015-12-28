package com.base.distribute.lock.generator.core;

/**
 * 
 * @author yubing
 *
 */
public enum DistributeLockType {
	
	ZOOKEEPER("zookeeper_distribute_lock"),
	REDIS("redis_distribute_lock");
	
	
	private final String value;
	
	private DistributeLockType(String value){
		this.value = value;
	}
	
	public String getValue(){
		return value;
	}

}
