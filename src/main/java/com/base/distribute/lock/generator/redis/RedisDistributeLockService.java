package com.base.distribute.lock.generator.redis;

import redis.clients.jedis.Jedis;
/**
 * 
 * @author yubing
 *
 */

public interface RedisDistributeLockService {
	
	Jedis getJedisResource() throws Exception;
	
	long setNx(String lockKey,String value) throws Exception;
	
	String getValue(String lockKey) throws Exception;
	
	void returnJedisResource() throws Exception;
	
	long delKey(String lockKey) throws Exception;
	
	String getSetValue(String lockKey,String value) throws Exception;

}
