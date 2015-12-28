package com.base.distribute.lock.generator.aop;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author yubing
 *
 */
public class LogInFileDistributeLockAopEntity extends
		AbstractDistributeLockAopEntity {

	private static Logger logger = LoggerFactory.getLogger(LogInFileDistributeLockAopEntity.class);
	

	@Override
	public void logDistributeLockInfo(Map<String,Object> params) {
		
		//at present, not support log distributeLock in File
		throw new UnsupportedOperationException();
	}


	@Override
	public String showDistributeLockAopEntityName() {
		// TODO Auto-generated method stub
		return null;
	}

}
