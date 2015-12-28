package com.base.distribute.lock.generator.async;

import java.util.Map;

/**
 * 
 * @author yubing
 *
 */
public class AsyncLogDistributeLockInfoService {
	
	public static void asyncLogDistributeLockInfoInDB(Map<String,Object> params){
		AsyncLogDistributeLockInfoInDBTask asyncLogDistributeLockInfoInDBTask = new AsyncLogDistributeLockInfoInDBTask(params);
		AsyncThreadPool.getInstance().submitTask(asyncLogDistributeLockInfoInDBTask);
	}
	
	public static void asyncLogDistributeLockInfoInMongo(Map<String,Object> params){
		AsyncLogDistributeLockInfoInMongoTask asyncLogDistributeLockInfoInMongoTask = new AsyncLogDistributeLockInfoInMongoTask(params);
		AsyncThreadPool.getInstance().submitTask(asyncLogDistributeLockInfoInMongoTask);
	}

}
