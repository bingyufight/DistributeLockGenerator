package com.base.distribute.lock.generator.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;


public class RedisDistributeLockServiceImpl implements
		RedisDistributeLockService {
	
	private static final Logger logger = LoggerFactory.getLogger(RedisDistributeLockServiceImpl.class);
	
	private RedisConnection redisConnection;
	
	private Jedis jedis;
	
	
	RedisDistributeLockServiceImpl(RedisConnection redisConnection){
		this.redisConnection = redisConnection;
	}
	

	public synchronized Jedis getJedisResource() throws Exception {
		if(jedis == null){
				jedis = redisConnection.getJedisRelativePool().getResource();
				jedis.select(redisConnection.getRedisConfigInfo().getSelectDbIndex());
		}
		return jedis;
	}

	public synchronized long setNx(String lockKey, String value) throws Exception {
			logger.info("execute setNx,lockKey:{},value{}",lockKey,value);
			return getJedisResource().setnx(lockKey, value);
	}

	public String getValue(String lockKey) throws Exception{
		logger.info("execute getValue,lockKey:{}",lockKey);
		return getJedisResource().get(lockKey);
	}

	@SuppressWarnings("deprecation")
	public synchronized void returnJedisResource() throws Exception{
		if(redisConnection.getJedisRelativePool() != null && jedis != null){
			redisConnection.getJedisRelativePool().returnResource(jedis);
		}
	}


	public synchronized long delKey(String lockKey) throws Exception {
		logger.info("delKey-----lockKey:{}",lockKey);
		return getJedisResource().del(lockKey);
	}


	public String getSetValue(String lockKey, String value) throws Exception {
		logger.info("getSetValue-----lockKey:{},value:{}",lockKey,value);
		return getJedisResource().getSet(lockKey, value);
	}

}
