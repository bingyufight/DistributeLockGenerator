package com.base.distribute.lock.generator.dbmodule.pojo;


/**
 * 
 * @author yubing
 *
 */
public enum DistributeLockTypeValueInDB {
	ZOOKEEPER_VALUE_IN_DB(1),
	REDIS_VALUE_IN_DB(2);
	
	
	private final int value;
	
	private DistributeLockTypeValueInDB(int value){
		this.value = value;
	}
	
	public int getValue(){
		return value;
	}

}
