package com.base.distribute.lock.generator.redis;

import java.util.UUID;

/**
 * 
 * @author yubing
 *
 */
public class RedisLockEntity {
	
	private String uuid;
	
	private String lockKey;
	
	private long lockTimeout;
	
	
	public RedisLockEntity(){
		uuid = UUID.randomUUID().toString();
	}

	public String getLockKey() {
		return lockKey;
	}

	public void setLockKey(String lockKey) {
		this.lockKey = lockKey;
	}

	public long getLockTimeout() {
		return lockTimeout;
	}

	public void setLockTimeout(long lockTimeout) {
		this.lockTimeout = lockTimeout;
	}
	
	

	public String getUuid() {
		return uuid;
	}

	

	@Override
	public String toString() {
		return "RedisLockEntity [lockKey=" + lockKey + ", lockTimeout="
				+ lockTimeout + "]";
	}
	
	
}
