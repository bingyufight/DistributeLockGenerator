package com.base.distribute.lock.generator.redis;


/**
 * 
 * @author yubing
 *
 */
public class RedisConfigInfo {
	
	private int maxActive;
	
	private int maxIdle;
	
	private int minIdle;
	
	private boolean testOnBrow;
	
	private boolean testOnReturn;
	
	private int maxWait;
	
	private int selectDbIndex;
	
	private int timeOut;
	
	private String redisHost;
	
	private int port;
	
	private String appName;
	
	private long lockTimeOut;
	
	private String lockSeparatedKey;

	public int getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}

	public int getMaxIdle() {
		return maxIdle;
	}

	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}

	public int getMinIdle() {
		return minIdle;
	}

	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}

	public boolean isTestOnBrow() {
		return testOnBrow;
	}

	public void setTestOnBrow(boolean testOnBrow) {
		this.testOnBrow = testOnBrow;
	}

	public boolean isTestOnReturn() {
		return testOnReturn;
	}

	public void setTestOnReturn(boolean testOnReturn) {
		this.testOnReturn = testOnReturn;
	}

	public int getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(int maxWait) {
		this.maxWait = maxWait;
	}

	public int getSelectDbIndex() {
		return selectDbIndex;
	}

	public void setSelectDbIndex(int selectDbIndex) {
		this.selectDbIndex = selectDbIndex;
	}

	public int getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

	public String getRedisHost() {
		return redisHost;
	}

	public void setRedisHost(String redisHost) {
		this.redisHost = redisHost;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}


	public long getLockTimeOut() {
		return lockTimeOut;
	}

	public void setLockTimeOut(long lockTimeOut) {
		this.lockTimeOut = lockTimeOut;
	}


	public String getLockSeparatedKey() {
		return lockSeparatedKey;
	}

	public void setLockSeparatedKey(String lockSeparatedKey) {
		this.lockSeparatedKey = lockSeparatedKey;
	}

	@Override
	public String toString() {
		return "RedisConfigInfo [maxActive=" + maxActive + ", maxIdle="
				+ maxIdle + ", minIdle=" + minIdle + ", testOnBrow="
				+ testOnBrow + ", testOnReturn=" + testOnReturn + ", maxWait="
				+ maxWait + ", selectDbIndex=" + selectDbIndex + ", timeOut="
				+ timeOut + ", redisHost=" + redisHost + ", port=" + port
				+ ", appName=" + appName + ", lockTimeOut=" + lockTimeOut
				+ ", lockSeparatedKey=" + lockSeparatedKey + "]";
	}

	
	

}
