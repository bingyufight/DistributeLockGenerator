package com.base.distribute.lock.generator.async;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.base.distribute.lock.generator.constant.DLGConstants;
import com.base.distribute.lock.generator.mongo.DistributeLockMongoDBObject;
import com.base.distribute.lock.generator.mongo.MongoDBObjectJavaBeanTransferUtil;
import com.base.distribute.lock.generator.mongo.MongoDBOperationService;
import com.base.distribute.lock.generator.util.PropertyUtils;
import com.mongodb.BasicDBObject;

/**
 * 
 * @author yubing
 *
 */
public class AsyncLogDistributeLockInfoInMongoTask implements Runnable{
	
	private static Logger logger = LoggerFactory.getLogger(AsyncLogDistributeLockInfoInMongoTask.class);
	
	private final Map<String,Object> paramHashMap ;
	
	private static String bundleResourceString = "mongodb";
	


	
	public AsyncLogDistributeLockInfoInMongoTask(Map<String,Object> paramHashMap){
		this.paramHashMap = paramHashMap;
	}

	public void run() {
		logger.info("start execute AsyncLogDistributeLockInfoInMongoTask run,paramHashMap:{}",paramHashMap);
		DistributeLockMongoDBObject distributeLockMongoDBObject =generateDistributeLockMongoDBObjectFromHashMap();
		
		logger.info("the param distributeLockMongoDBObject:{}",distributeLockMongoDBObject.toString());
		try {
			String dbCollectionName = PropertyUtils.getPropertyString(bundleResourceString, "mongo.db.collection");
			MongoDBOperationService.getSingleInstance().saveDBObject(dbCollectionName, MongoDBObjectJavaBeanTransferUtil.javaBean2DBObject(distributeLockMongoDBObject));
		} catch (Exception e) {
			logger.error("updateOrSave mongo occur error",e);
		}
		
		
	}
	
	
	private DistributeLockMongoDBObject generateDistributeLockMongoDBObjectFromHashMap(){
		DistributeLockMongoDBObject distributeLockMongoDBObject = new DistributeLockMongoDBObject();
		distributeLockMongoDBObject.setRequestLockTime(paramHashMap.containsKey(DLGConstants.REQUEST_LOCK_TIME)?(Date)paramHashMap.get(DLGConstants.REQUEST_LOCK_TIME) : null);
		distributeLockMongoDBObject.setAppUseDistributeLock(paramHashMap.containsKey(DLGConstants.APP_USE_DISTRIBUTE_LOCK)? (String)paramHashMap.get(DLGConstants.APP_USE_DISTRIBUTE_LOCK) : null);
		distributeLockMongoDBObject.setClientIp(paramHashMap.containsKey(DLGConstants.CLIENT_IP)? (String)paramHashMap.get(DLGConstants.CLIENT_IP) : null);
		distributeLockMongoDBObject.setLockClientUuid(paramHashMap.containsKey(DLGConstants.LOCK_CLIENT_UUID)? (String)paramHashMap.get(DLGConstants.LOCK_CLIENT_UUID) : null);
		distributeLockMongoDBObject.setLockCriticalKey(paramHashMap.containsKey(DLGConstants.LOCK_CRITICAL_KEY)? (String)paramHashMap.get(DLGConstants.LOCK_CRITICAL_KEY) : null);
		distributeLockMongoDBObject.setLockType(paramHashMap.containsKey(DLGConstants.LOCK_TYPE)? (Integer)paramHashMap.get(DLGConstants.LOCK_TYPE) : null);
		distributeLockMongoDBObject.setAcquireLockTime(paramHashMap.containsKey(DLGConstants.ACQUIRE_LOCK_TIME) ? (Date)paramHashMap.get(DLGConstants.ACQUIRE_LOCK_TIME): null);
		distributeLockMongoDBObject.setReleaseLockTime(paramHashMap.containsKey(DLGConstants.RELEASE_LOCK_TIME) ? (Date)paramHashMap.get(DLGConstants.RELEASE_LOCK_TIME): null);
		distributeLockMongoDBObject.setIsOwnDistributeLock(paramHashMap.containsKey(DLGConstants.ISOWN_DISTRIBUTE_LOCK) ? (Integer)paramHashMap.get(DLGConstants.ISOWN_DISTRIBUTE_LOCK): null);
		distributeLockMongoDBObject.setCallMethodName(paramHashMap.containsKey(DLGConstants.CALL_METHOD_NAME) ? (String)paramHashMap.get(DLGConstants.CALL_METHOD_NAME): null);
		distributeLockMongoDBObject.setState(1);
		distributeLockMongoDBObject.setUpdateTime(new Date());
		
		return distributeLockMongoDBObject;
	}

}
