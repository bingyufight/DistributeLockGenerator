package com.base.distribute.lock.generator.dbmodule;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.base.distribute.lock.generator.dbmodule.pojo.DistributeLockLogInfo;

/**
 * 
 * @author yubing
 *
 */
public interface DistributeLockLogInfoMapper {

	public int saveDistributeLockLogInfo(DistributeLockLogInfo distributeLockLogInfo);
	
	public int updateDistributeLockLogInfo(DistributeLockLogInfo distributeLockLogInfo);
	
	public List<DistributeLockLogInfo> getDistributeLockLogInfoByUUID(@Param("lockClientUuid") String lockClientUuid);
}
