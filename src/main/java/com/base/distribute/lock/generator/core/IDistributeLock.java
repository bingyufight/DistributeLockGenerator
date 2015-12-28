package com.base.distribute.lock.generator.core;

import com.base.distribute.lock.generator.exception.DistributeLockException;

/**
 * 
 * @author yubing
 *
 */

public interface IDistributeLock {
		
	boolean acquireLock() throws DistributeLockException;
	
	boolean releaseLock() throws DistributeLockException;
	
	boolean isLocked() throws DistributeLockException;
	
	boolean tryAcquireLock(long duration) throws DistributeLockException;
}
