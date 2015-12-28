package com.base.distribute.lock.generator.redis;

/**
 * 
 * @author yubing
 *
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.base.distribute.lock.generator.constant.DLGConstants;
import com.base.distribute.lock.generator.core.DistributeLockType;
import com.base.distribute.lock.generator.util.ConfigUtil;

public class RedisConnection {
	
	private static final Logger logger = LoggerFactory.getLogger(RedisConnection.class);
	
	private final RedisConfigInfo redisConfigInfo;
	
	
	
	private JedisPool jedisPool;
	
	public RedisConnection(){
		redisConfigInfo = new RedisConfigInfo();
		
		redisConfigInfo.setMaxActive(Integer.parseInt(ConfigUtil.getProperty(DistributeLockType.REDIS.getValue(), "redis.max.active", DLGConstants.DEFAULT_REDIS_MAX_ACTIVE)));
		redisConfigInfo.setMaxIdle(Integer.parseInt(ConfigUtil.getProperty(DistributeLockType.REDIS.getValue(), "redis.max.idle", DLGConstants.DEFAULT_REDIS_MAX_IDLE)));
		redisConfigInfo.setMinIdle(Integer.parseInt(ConfigUtil.getProperty(DistributeLockType.REDIS.getValue(), "redis.min.idle", DLGConstants.DEFAULT_REDIS_MIN_IDLE)));
		redisConfigInfo.setMaxWait(Integer.parseInt(ConfigUtil.getProperty(DistributeLockType.REDIS.getValue(), "redis.max.wait", DLGConstants.DEFAULT_REDIS_MAX_WAIT)));
		redisConfigInfo.setAppName(ConfigUtil.getProperty(DistributeLockType.REDIS.getValue(), "app.use.redis.lock", DLGConstants.DEFAULT_REDIS_APP_NAME));
		redisConfigInfo.setPort(Integer.parseInt(ConfigUtil.getProperty(DistributeLockType.REDIS.getValue(), "redis.port", DLGConstants.DEFAULT_REDIS_PORT)));
		redisConfigInfo.setRedisHost(ConfigUtil.getProperty(DistributeLockType.REDIS.getValue(), "redis.host", DLGConstants.DEFAULT_REDIS_HOST));
		redisConfigInfo.setSelectDbIndex(Integer.parseInt(ConfigUtil.getProperty(DistributeLockType.REDIS.getValue(), "redis.select.db.index", DLGConstants.DEFAULT_REDIS_SELECT_DB_INDEX)));
		redisConfigInfo.setTestOnBrow(Boolean.parseBoolean(ConfigUtil.getProperty(DistributeLockType.REDIS.getValue(), "redis.test.onBrow", DLGConstants.DEFAULT_REDIS_TEST_ON_BROW)));
		redisConfigInfo.setTestOnReturn(Boolean.parseBoolean(ConfigUtil.getProperty(DistributeLockType.REDIS.getValue(), "redis.test.onReturn", DLGConstants.DEFAULT_REDIS_TEST_ON_RETURN)));
		redisConfigInfo.setTimeOut(Integer.parseInt(ConfigUtil.getProperty(DistributeLockType.REDIS.getValue(), "redis.timeout", DLGConstants.DEFAULT_REDIS_TIMEOUT)));
		redisConfigInfo.setLockTimeOut(Long.parseLong(ConfigUtil.getProperty(DistributeLockType.REDIS.getValue(), "redis.own.lock.timeout", DLGConstants.DEFAULT_REDIS_OWN_LOCK_TIMEOUT)));
		redisConfigInfo.setLockSeparatedKey(ConfigUtil.getProperty(DistributeLockType.REDIS.getValue(), "redis.lock.separated.key", DLGConstants.DEFAULT_REDIS_SEPARATED_KEY));
		
		
	}
	
	
	public synchronized JedisPool getJedisRelativePool(){
		if(jedisPool == null){
			try{
				if(jedisPool == null){
					// build JedisPoolConfig instance
					JedisPoolConfig config = new JedisPoolConfig();
					config.setMaxIdle(redisConfigInfo.getMaxIdle());
					config.setMinIdle(redisConfigInfo.getMinIdle());
					config.setMaxWaitMillis(redisConfigInfo.getMaxWait());
					config.setTestOnBorrow(redisConfigInfo.isTestOnBrow());
					config.setTestOnReturn(redisConfigInfo.isTestOnReturn());
					
					jedisPool = new JedisPool(config,redisConfigInfo.getRedisHost(),redisConfigInfo.getPort(),redisConfigInfo.getTimeOut()); 
				}
			}catch(Exception e){
				logger.error("getJedisResource occur unknow error",e);
				jedisPool = null;
			}
		}
		
		return jedisPool;
	}
	
	public synchronized RedisDistributeLockService getConnectionRedisDistributeLockService(){
		return new RedisDistributeLockServiceImpl(this);
	}
    

	public RedisConfigInfo getRedisConfigInfo() {
		return redisConfigInfo;
	}
	
	

}
