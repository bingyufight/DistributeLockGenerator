package com.base.distribute.lock.generator.mongo;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.base.distribute.lock.generator.util.PropertyUtils;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;



/**
 * 
 * @author yubing
 *
 */
public class MongoDBOperationService {
	private static Logger logger = LoggerFactory.getLogger(MongoDBOperationService.class);
	
	private static MongoClient mongo = null;
	
	private static String bundleResourceString = "mongodb";
	
	private MongoDBOperationService(){
		try {
			String  mongoHost = PropertyUtils.getPropertyString(bundleResourceString, "mongo.db.host");
		    Integer mongoPort = Integer.valueOf(PropertyUtils.getPropertyString(bundleResourceString, "mongo.db.port"));
            mongo = new MongoClient(new ServerAddress(mongoHost, mongoPort),getConfOptions());
            logger.info("init mongoDB success!");
 
        } catch (Exception e) {
        	logger.error("init mongoDB occur error", e);
            e.printStackTrace();
        }
	}

	
	private MongoClientOptions getConfOptions() {
		return new MongoClientOptions.Builder().socketKeepAlive(true) 
		.connectTimeout(Integer.valueOf(PropertyUtils.getPropertyString(bundleResourceString, "mongo.db.connection.timeout")))
		.socketTimeout(Integer.valueOf(PropertyUtils.getPropertyString(bundleResourceString, "mongo.db.socket.timeout"))) 
		.readPreference(ReadPreference.primary())
		.autoConnectRetry(false)
		.connectionsPerHost(Integer.valueOf(PropertyUtils.getPropertyString(bundleResourceString, "mongo.db.connections.perhost")))
		.maxWaitTime(Integer.valueOf(PropertyUtils.getPropertyString(bundleResourceString, "mongo.db.max.wait.time")))
		.threadsAllowedToBlockForConnectionMultiplier(Integer.valueOf(PropertyUtils.getPropertyString(bundleResourceString, "mongo.db.threads.allowed.block.connection.multiplier"))) 
		.writeConcern(WriteConcern.NORMAL).build();
	}
	
	private static class MongoDBOperationServiceHolder{
		private static MongoDBOperationService instance = new MongoDBOperationService();
	}
	
	public static MongoDBOperationService getSingleInstance(){
		return MongoDBOperationServiceHolder.instance;
	}
	
	
	private DB getMongoDB(){
		try{
			if(mongo != null){
				String dbName = PropertyUtils.getPropertyString(bundleResourceString, "mongo.db.name");
				String userName = PropertyUtils.getPropertyString(bundleResourceString, "mongo.db.user");
				String password = PropertyUtils.getPropertyString(bundleResourceString,"mongo.db.pass");
				DB db= mongo.getDB(dbName);
				db.authenticate(userName, password.toCharArray());
				return db;
			}
		}catch(Exception e){
			logger.error("getMongoDB occur error",e);
		}
		
		return null;
	}
	
	private DBCollection getCollection(String collectionName){
		 try {
             
	            if(getMongoDB() != null){
	            	return getMongoDB().getCollection(collectionName);
	            }
	             
	        } catch (Exception e) {
	        	logger.error("getCollection occur error",e);
	        }
	 
	        return null;
	}
	
	
	public void saveDBObject(String collectionName,DBObject dbObject){
		 try {
              	DBCollection dbCollection = getCollection(collectionName);
              	if(dbCollection != null){
              		dbCollection.save(dbObject);
              	}
	             
	        } catch (Exception e) {
	            logger.error("saveDBObject occur error", e);
	        }
	}
	
	
    public List<DBObject> find(String name, DBObject obj, int limit) {
         
        try {
             
            DBCollection collection = getCollection(name);
            DBCursor c = collection.find(obj).limit(limit);
             
            if (c != null){
                 
                List<DBObject> list = new ArrayList<DBObject>();
                list = c.toArray();
                 
                return list;
                 
            }
             
        } catch (Exception e) {
        	logger.error("find limit occur error", e);
        }
         
        return null;
         
    }

    public List<DBObject> find(String name, DBObject where) {
         
        try {
             
            DBCursor c = getCollection(name).find(where);
             
            if (c != null) {
                 
                List<DBObject> list = new ArrayList<DBObject>();
                list = c.toArray();
                 
                return list;
                 
            }
             
        } catch (Exception e) {
        	logger.error("find where occur error", e);
        }
 
        return null;
 
    }
    
    
    public void updateOrSave(String collectionName, DBObject set, DBObject where) {
    	 
        try {
             
            getCollection(collectionName).update(where, set, true, false);
             
        } catch (Exception e) {
            logger.error("updateOrInsert occur error",e);
        }
 
    }
     
	
    public void close() {
         
        try {
             
            if (mongo != null) {
                mongo.close();
                logger.info("MongoClient has been closed...");
            }
             
        } catch (Exception e) {
           logger.error("mongo close occur unkown error",e);
        }
 
    }
	
	
	
}
