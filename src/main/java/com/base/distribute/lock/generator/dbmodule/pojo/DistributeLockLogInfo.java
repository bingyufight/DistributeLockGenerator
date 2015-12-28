package com.base.distribute.lock.generator.dbmodule.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author yubing
 *
 */
public class DistributeLockLogInfo implements Serializable {

	private static final long serialVersionUID = 930487548884072541L;
	
	private Long id;
	
	private String lockClientUuid;
	
	private String appUseDistributeLock;
	
	private String clientIp;
	
	private String lockCriticalKey;
	
	private Integer lockType;
	
	private Date requestLockTime;
	
	private Integer isOwnDistributeLock;
	
	private Date acquireLockTime;
	
	private Date releaseLockTime;
	
	private String callMethodName;
	
	private Integer state;
	
	private Date createTime;
	
	private Date updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAppUseDistributeLock() {
		return appUseDistributeLock;
	}

	public void setAppUseDistributeLock(String appUseDistributeLock) {
		this.appUseDistributeLock = appUseDistributeLock;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getLockCriticalKey() {
		return lockCriticalKey;
	}

	public void setLockCriticalKey(String lockCriticalKey) {
		this.lockCriticalKey = lockCriticalKey;
	}

	public Integer getLockType() {
		return lockType;
	}

	public void setLockType(Integer lockType) {
		this.lockType = lockType;
	}

	public Date getRequestLockTime() {
		return requestLockTime;
	}

	public void setRequestLockTime(Date requestLockTime) {
		this.requestLockTime = requestLockTime;
	}

	public Integer getIsOwnDistributeLock() {
		return isOwnDistributeLock;
	}

	public void setIsOwnDistributeLock(Integer isOwnDistributeLock) {
		this.isOwnDistributeLock = isOwnDistributeLock;
	}

	public Date getAcquireLockTime() {
		return acquireLockTime;
	}

	public void setAcquireLockTime(Date acquireLockTime) {
		this.acquireLockTime = acquireLockTime;
	}

	public Date getReleaseLockTime() {
		return releaseLockTime;
	}

	public void setReleaseLockTime(Date releaseLockTime) {
		this.releaseLockTime = releaseLockTime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getLockClientUuid() {
		return lockClientUuid;
	}

	public void setLockClientUuid(String lockClientUuid) {
		this.lockClientUuid = lockClientUuid;
	}

	public String getCallMethodName() {
		return callMethodName;
	}

	public void setCallMethodName(String callMethodName) {
		this.callMethodName = callMethodName;
	}

	@Override
	public String toString() {
		return "DistributeLockLogInfo [id=" + id + ", lockClientUuid="
				+ lockClientUuid + ", appUseDistributeLock="
				+ appUseDistributeLock + ", clientIp=" + clientIp
				+ ", lockCriticalKey=" + lockCriticalKey + ", lockType="
				+ lockType + ", requestLockTime=" + requestLockTime
				+ ", isOwnDistributeLock=" + isOwnDistributeLock
				+ ", acquireLockTime=" + acquireLockTime + ", releaseLockTime="
				+ releaseLockTime + ", callMethodName=" + callMethodName
				+ ", state=" + state + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + "]";
	}
	
	
	
	
	

}
