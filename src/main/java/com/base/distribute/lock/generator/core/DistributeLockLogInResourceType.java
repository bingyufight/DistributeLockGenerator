package com.base.distribute.lock.generator.core;

/**
 * 
 * @author yubing
 *
 */
public enum DistributeLockLogInResourceType {
	MYSQL("mysql"),
	MONGO("mongo");
	
	
	private final String value;
	
	private DistributeLockLogInResourceType(String value){
		this.value = value;
	}
	
	public String getValue(){
		return value;
	}
}
