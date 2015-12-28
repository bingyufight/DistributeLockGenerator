package com.base.distribute.lock.generator.async;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.base.distribute.lock.generator.constant.DLGConstants;
import com.base.distribute.lock.generator.dbmodule.DBOperationService;
import com.base.distribute.lock.generator.dbmodule.pojo.DistributeLockLogInfo;

/**
 * 
 * @author yubing
 *
 */
public class AsyncLogDistributeLockInfoInDBTask implements Runnable {

	private static Logger logger = LoggerFactory.getLogger(AsyncLogDistributeLockInfoInDBTask.class);
	
	private final Map<String,Object> paramHashMap ;

	
	public AsyncLogDistributeLockInfoInDBTask(Map<String,Object> paramHashMap){
		this.paramHashMap = paramHashMap;
	}
	
	
	public void run() {
		logger.info("start execute AsyncLogDistributeLockInfoInDBTask run,paramHashMap:{}",paramHashMap);
		DistributeLockLogInfo distributeLockLogInfo = generateDistributeLockLogInfoFromHashMap();
		
		logger.info("the param distributeLockLogInfo:{}",distributeLockLogInfo.toString());
		
		if(distributeLockLogInfo.getLockClientUuid() !=null){
		
				DBOperationService.saveDistributeLockLogInfo(distributeLockLogInfo);
		}

	}
	
	
	private DistributeLockLogInfo generateDistributeLockLogInfoFromHashMap(){
		DistributeLockLogInfo distributeLockLogInfo = new DistributeLockLogInfo();
		distributeLockLogInfo.setRequestLockTime(paramHashMap.containsKey(DLGConstants.REQUEST_LOCK_TIME)?(Date)paramHashMap.get(DLGConstants.REQUEST_LOCK_TIME) : null);
		distributeLockLogInfo.setAppUseDistributeLock(paramHashMap.containsKey(DLGConstants.APP_USE_DISTRIBUTE_LOCK)? (String)paramHashMap.get(DLGConstants.APP_USE_DISTRIBUTE_LOCK) : null);
		distributeLockLogInfo.setClientIp(paramHashMap.containsKey(DLGConstants.CLIENT_IP)? (String)paramHashMap.get(DLGConstants.CLIENT_IP) : null);
		distributeLockLogInfo.setLockClientUuid(paramHashMap.containsKey(DLGConstants.LOCK_CLIENT_UUID)? (String)paramHashMap.get(DLGConstants.LOCK_CLIENT_UUID) : null);
		distributeLockLogInfo.setLockCriticalKey(paramHashMap.containsKey(DLGConstants.LOCK_CRITICAL_KEY)? (String)paramHashMap.get(DLGConstants.LOCK_CRITICAL_KEY) : null);
		distributeLockLogInfo.setLockType(paramHashMap.containsKey(DLGConstants.LOCK_TYPE)? (Integer)paramHashMap.get(DLGConstants.LOCK_TYPE) : null);
		distributeLockLogInfo.setAcquireLockTime(paramHashMap.containsKey(DLGConstants.ACQUIRE_LOCK_TIME) ? (Date)paramHashMap.get(DLGConstants.ACQUIRE_LOCK_TIME): null);
		distributeLockLogInfo.setReleaseLockTime(paramHashMap.containsKey(DLGConstants.RELEASE_LOCK_TIME) ? (Date)paramHashMap.get(DLGConstants.RELEASE_LOCK_TIME): null);
		distributeLockLogInfo.setIsOwnDistributeLock(paramHashMap.containsKey(DLGConstants.ISOWN_DISTRIBUTE_LOCK) ? (Integer)paramHashMap.get(DLGConstants.ISOWN_DISTRIBUTE_LOCK): null);
		distributeLockLogInfo.setCallMethodName(paramHashMap.containsKey(DLGConstants.CALL_METHOD_NAME) ? (String)paramHashMap.get(DLGConstants.CALL_METHOD_NAME): null);
		distributeLockLogInfo.setState(1);
		distributeLockLogInfo.setUpdateTime(new Date());
		
		return distributeLockLogInfo;
	}

}
