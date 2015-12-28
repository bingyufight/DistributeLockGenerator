package com.base.distribute.lock.generator.constant;

/**
 * 
 * @author yubing
 *
 */
public class DLGConstants {
	
	
	public final static String DEFAULT_TIME_OUT_TIME = "50000";
	
	public final static String DEFAULT_APP_NAME = "distributeLockDemo";
	
	public final static String DEFAULT_LOCK_PATH = "/zklock";
	
	public final static String DEFAULT_ZOOKEEPER_ADDRESS = "127.0.0.1:2181";
	
	public final static String DEFAULT_REDIS_MAX_ACTIVE = "1024";
	
	public final static String DEFAULT_REDIS_MAX_IDLE = "150";
	
	public final static String DEFAULT_REDIS_MIN_IDLE ="100";
	
	public final static String DEFAULT_REDIS_TEST_ON_BROW = "false";
	
	public final static String DEFAULT_REDIS_TEST_ON_RETURN = "false";
	
	public final static String DEFAULT_REDIS_MAX_WAIT ="20000";
	
	public final static String DEFAULT_REDIS_SELECT_DB_INDEX = "0";
	
	public final static String DEFAULT_REDIS_TIMEOUT = "50000";
	
	public final static String DEFAULT_REDIS_HOST = "127.0.0.1";
	
	public final static String DEFAULT_REDIS_PORT = "6379";
	
	public final static String DEFAULT_REDIS_APP_NAME = "redisDistributeLockDemo";
	
	public final static String DEFAULT_REDIS_OWN_LOCK_TIMEOUT = "5000000";
	
	public final static String DEFAULT_REDIS_SEPARATED_KEY = "locked";
	
	public final static int  THREAD_POOL_SIZE = 40;
	
	
	
	public final static String REQUEST_LOCK_TIME = "requestLockTime";
	public final static String APP_USE_DISTRIBUTE_LOCK = "appUseDistributeLock";
	public final static String CLIENT_IP = "clientIp";
	public final static String LOCK_CLIENT_UUID = "lockClientUuid";
	public final static String LOCK_CRITICAL_KEY = "lockCriticalKey";
	public final static String LOCK_TYPE = "lockType";
	public final static String ACQUIRE_LOCK_TIME = "acquireLockTime";
	public final static String RELEASE_LOCK_TIME = "releaseLockTime";
	public final static String ISOWN_DISTRIBUTE_LOCK = "isOwnDistributeLock";
	public final static String CALL_METHOD_NAME= "callMethodName";
	
	

}
