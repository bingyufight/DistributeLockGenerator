package com.base.distribute.lock.generator.zookeeper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.UUID;

import com.base.distribute.lock.generator.exception.DistributeLockException;

public class ZookeeperNodeInfo implements Comparable<ZookeeperNodeInfo> {
	
	private static Logger logger = LoggerFactory.getLogger(ZookeeperNodeInfo.class);
	
	
	private String uuid;
	
	private String zkNodeId;
	
	private Long clientSequenceId;
	
	private String prefixPath;

	
	public ZookeeperNodeInfo(String zookeeperCreateNodeId) throws DistributeLockException{
		
		if(zookeeperCreateNodeId == null){
			throw new DistributeLockException("zookeeper node id dosen't exist");
		}
		
		prefixPath = zkNodeId = zookeeperCreateNodeId;
		
		int splitIndex = zkNodeId.lastIndexOf("_");
		
		if(splitIndex >= 0){
			prefixPath = zkNodeId.substring(0, splitIndex);
			try{
				clientSequenceId = Long.parseLong(zkNodeId.substring(splitIndex + 1));
			} catch(Exception e){
				logger.error("can not parse clientSequenceId",e);
				throw new DistributeLockException("can not parse clientSequenceId,system error");
			}
		}
		
		uuid = UUID.randomUUID().toString();
		
		
		
	}
	
	
	public int compareTo(ZookeeperNodeInfo o) {
		
		int firstCompare = this.prefixPath.compareTo(o.getPrefixPath());
		if(firstCompare == 0){ //first compare prefixPath, only prefixPath equals then compare clientSequenceId
			return this.getClientSequenceId().compareTo(o.getClientSequenceId());
		}
		return firstCompare;
	}

     


	public String getZkNodeId() {
		return zkNodeId;
	}


	public Long getClientSequenceId() {
		return clientSequenceId;
	}





	public String getPrefixPath() {
		return prefixPath;
	}

	
	

	public String getUuid() {
		return uuid;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((zkNodeId == null) ? 0 : zkNodeId.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ZookeeperNodeInfo other = (ZookeeperNodeInfo) obj;
		if (zkNodeId == null) {
			if (other.zkNodeId != null)
				return false;
		} else if (!zkNodeId.equals(other.zkNodeId))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "ZookeeperNodeInfo [zkNodeId=" + zkNodeId + "]";
	}

    
	

	
	
	
	
	
	

}
