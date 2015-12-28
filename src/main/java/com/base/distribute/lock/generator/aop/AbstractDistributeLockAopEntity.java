package com.base.distribute.lock.generator.aop;

/**
 * 
 * @author yubing
 *
 */

import java.util.Map;


public abstract class AbstractDistributeLockAopEntity {
	
	public abstract String showDistributeLockAopEntityName();
	
	public abstract void logDistributeLockInfo(Map<String,Object> params);

}
