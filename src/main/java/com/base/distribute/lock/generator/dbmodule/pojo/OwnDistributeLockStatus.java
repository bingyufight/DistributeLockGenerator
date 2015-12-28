package com.base.distribute.lock.generator.dbmodule.pojo;


/**
 * 
 * @author yubing
 *
 */
public enum OwnDistributeLockStatus {
	UNLOCKED(0),
	LOCKED(1);
	
	
	private final int value;
	
	private OwnDistributeLockStatus(int value){
		this.value = value;
	}
	
	public int getValue(){
		return value;
	}
}
