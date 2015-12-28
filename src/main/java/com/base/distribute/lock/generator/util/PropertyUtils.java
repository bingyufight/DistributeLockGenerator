package com.base.distribute.lock.generator.util;

import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * @author yubing
 *
 */
public class PropertyUtils {
	
	private static Logger logger = LoggerFactory.getLogger(PropertyUtils.class);
	
	public static String getPropertyString(String bundleFileName,String propertyKey){
		 try {
             
	            return ResourceBundle.getBundle(bundleFileName).getString(propertyKey);
	             
	        } catch (Exception e) {
	        	logger.error("execute getPropertyString occur error", e);
	            return "";
	        }
	}
	

}
