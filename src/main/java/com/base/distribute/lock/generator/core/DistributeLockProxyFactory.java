package com.base.distribute.lock.generator.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.base.distribute.lock.generator.aop.AbstractDistributeLockAopEntity;
import com.base.distribute.lock.generator.aop.DistributeLockAopInvocationHandler;
import com.base.distribute.lock.generator.aop.DistributeLockAopTag;
import com.base.distribute.lock.generator.aop.LogInDBDistributeLockAopEntity;
import com.base.distribute.lock.generator.aop.LogInMongoDistributeLockAopEntity;
import com.base.distribute.lock.generator.util.PropertyUtils;

/**
 * 
 * @author yubing
 *
 */
public class DistributeLockProxyFactory {
	
	private static final Logger logger = LoggerFactory.getLogger(DistributeLockProxyFactory.class);
	
	private static final String DISTRIBUTE_LOCK_SYSTEM = "distribute_lock_system";
	
	@SuppressWarnings("unchecked")
	public static <T> T getDistributeLockInstance(Class<T> classType){
		logger.info("start excuting getDistributeLockInstance,className:{}",classType.getName());
		
		T distributeLockInstance = (T) generateClassInstance(classType);
		
		if(isOpenLog()){ // first check have opened the log
			String logInResource = PropertyUtils.getPropertyString(DISTRIBUTE_LOCK_SYSTEM,"distribute.lock.system.log.in.resource");
			AbstractDistributeLockAopEntity abstractDistributeLockAopEntity = null;
			if(DistributeLockLogInResourceType.MYSQL.getValue().equals(logInResource)){
				 abstractDistributeLockAopEntity = (AbstractDistributeLockAopEntity)generateClassInstance(LogInDBDistributeLockAopEntity.class);
			
			} else if(DistributeLockLogInResourceType.MONGO.getValue().equals(logInResource)){
				 abstractDistributeLockAopEntity = (AbstractDistributeLockAopEntity)generateClassInstance(LogInMongoDistributeLockAopEntity.class);
		
			} else { // if user not setting the distribute.lock.system.log.in.resource value,we may use the defaultAopHandleClass
					Method[] distributeLockClassMethods = classType.getMethods(); // retrieve the all public methods
					if(distributeLockClassMethods != null && distributeLockClassMethods.length > 0){
						for(Method distributeLockClassMethod: distributeLockClassMethods){
							DistributeLockAopTag distributeLockAopTag = distributeLockClassMethod.getAnnotation(DistributeLockAopTag.class);
							if(distributeLockAopTag != null){
							   abstractDistributeLockAopEntity = (AbstractDistributeLockAopEntity)generateClassInstance(distributeLockAopTag.defaultAopHandleClass());
							    break;
						
							}
						}
					}
				}
			
			return (T) Proxy.newProxyInstance(
					distributeLockInstance.getClass().getClassLoader(), 
					distributeLockInstance.getClass().getInterfaces(), 
                    new DistributeLockAopInvocationHandler(distributeLockInstance,abstractDistributeLockAopEntity)
                   );
			
		}

		
		return distributeLockInstance;
	}
	
	
	private static Object generateClassInstance(final Class classType){
		logger.info("start excuting generateClassInstance,className:{}",classType.getName());
		try{
			Constructor constructor = classType.getConstructor();
			return constructor.newInstance(new Class[]{});
		}catch (NoSuchMethodException e) {
			logger.error("generateDistributeLockInstance occur NoSuchMethodException",e);
		}catch (SecurityException e) {
			logger.error("generateDistributeLockInstance occur SecurityException",e);
		} catch (InstantiationException e){
			logger.error("generateDistributeLockInstance occur InstantiationException",e);
		} catch(Exception e){
			logger.error("generateDistributeLockInstance occur Unkown Exception",e);
		}
		return null;
		
	}
	
	
	private static boolean isOpenLog(){
		try{
			String isOpenLogFlag = PropertyUtils.getPropertyString(DISTRIBUTE_LOCK_SYSTEM,"distribute.lock.system.open.log");
			logger.info("isOpenLogFlag=" + isOpenLogFlag);
			return Boolean.parseBoolean(isOpenLogFlag);
		}catch(Exception e){
			
		}
		return false;
	}

}
