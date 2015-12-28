package com.base.distribute.lock.generator.mongo;

import java.lang.reflect.Field;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;


/**
 * 
 * @author yubing
 *
 */
public class MongoDBObjectJavaBeanTransferUtil {
	
	 public static <T> DBObject javaBean2DBObject(T javaBean) throws Exception {  
	   if (javaBean == null) {  
	     return null;  
	   }  
	   DBObject dbObject = new BasicDBObject();  
	   Field[] fields = javaBean.getClass().getDeclaredFields();  
	   for (Field field : fields) {  
	     String varName = field.getName();  
	     boolean accessFlag = field.isAccessible();  
	     if (!accessFlag) {  
	       field.setAccessible(true);  
	     }  
	     Object param = field.get(javaBean);  
	     if (param == null) {  
	       continue;  
	     } else if (param instanceof Integer) {//判断变量的类型  
	       int value = ((Integer) param).intValue();  
	       dbObject.put(varName, value);  
	     } else if (param instanceof String) {  
	       String value = (String) param;  
	       dbObject.put(varName, value);  
	     } else if (param instanceof Double) {  
	       double value = ((Double) param).doubleValue();  
	       dbObject.put(varName, value);  
	     } else if (param instanceof Float) {  
	       float value = ((Float) param).floatValue();  
	       dbObject.put(varName, value);  
	     } else if (param instanceof Long) {  
	       long value = ((Long) param).longValue();  
	       dbObject.put(varName, value);  
	     } else if (param instanceof Boolean) {  
	       boolean value = ((Boolean) param).booleanValue();  
	       dbObject.put(varName, value);  
	     } else if (param instanceof Date) {  
	       Date value = (Date) param;  
	       dbObject.put(varName, value);  
	     }  
	     field.setAccessible(accessFlag);  
	   }  
	   return dbObject;  
	 }  
	 
	 public static <T> T dbObject2JavaBean(DBObject dbObject, T javaBean) throws Exception{  
	   if (javaBean == null) {  
	     return null;  
	   }  
	   Field[] fields = javaBean.getClass().getDeclaredFields();  
	   for (Field field : fields) {  
	     String varName = field.getName();  
	     Object object = dbObject.get(varName);  
	     if (object != null) {  
	       BeanUtils.setProperty(javaBean, varName, object);  
	     }  
	   }  
	   return javaBean;  
	 }  

}
