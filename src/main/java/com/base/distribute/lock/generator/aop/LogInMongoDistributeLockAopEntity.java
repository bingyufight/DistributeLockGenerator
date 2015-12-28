package com.base.distribute.lock.generator.aop;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.base.distribute.lock.generator.async.AsyncLogDistributeLockInfoService;


/**
 * 
 * @author yubing
 *
 */
public class LogInMongoDistributeLockAopEntity extends AbstractDistributeLockAopEntity {

	private static Logger logger = LoggerFactory.getLogger(LogInMongoDistributeLockAopEntity.class);
	
	@Override
	public void logDistributeLockInfo(Map<String, Object> params) {
		//At present, the statistical log information and distributed lock code written in the back in the development of distributed transaction framework will be decoupled.
		logger.info("start excuting mongodb logDistributeLockInfo,params:{}",params);
		if(params != null && params.size() > 0){ 
			AsyncLogDistributeLockInfoService.asyncLogDistributeLockInfoInMongo(params);
		}
		
		
	}

	@Override
	public String showDistributeLockAopEntityName() {
		// TODO Auto-generated method stub
		return "LogInMongoDistributeLockAopEntity";
	}

}


