package com.base.distribute.lock.generator.zookeeper;

/**
 * 
 * @author yubing
 *
 */
public class ZookeeperConfigInfo {
	
	private String zookeeperAddress;
	
	private String appName;
	
	private int timeOut;
	
	private String lockPath;

	public String getZookeeperAddress() {
		return zookeeperAddress;
	}

	public void setZookeeperAddress(String zookeeperAddress) {
		this.zookeeperAddress = zookeeperAddress;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public int getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

	public String getLockPath() {
		return lockPath;
	}

	public void setLockPath(String lockPath) {
		this.lockPath = lockPath;
	}

	@Override
	public String toString() {
		return "ZookeeperConfigInfo [zookeeperAddress=" + zookeeperAddress
				+ ", appName=" + appName + ", timeOut=" + timeOut
				+ ", lockPath=" + lockPath + "]";
	}
	
	
	

}
