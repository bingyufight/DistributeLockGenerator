package com.base.distribute.lock.generator.dbmodule;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.base.distribute.lock.generator.zookeeper.ZookeeperDistributeLockServiceImpl;

/**
 * 
 * @author yubing
 *
 */
public class MybatisSqlSessionFactoryUtil {
	
	private static Logger logger = LoggerFactory.getLogger(ZookeeperDistributeLockServiceImpl.class);
	
	private SqlSessionFactory sqlSessionFactory; 
	
	private MybatisSqlSessionFactoryUtil() {
		try{
			 String resource = "mybatis/mybatis-config.xml";  
	         InputStream inputStream = Resources.getResourceAsStream(resource);  
	         sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream); 
		} catch(Exception e){
			sqlSessionFactory = null;
			logger.error("init sqlSessionFactory occur error",e);
		}
		 
	}
	
	/**
     *   use java inner static class to ensure only generate the only one instance
     */
    private static class MybatisSqlSessionFactoryUtilHolder{
        /**
         * use java inner static class to ensure only generate the only one instance,the JVM will ensure the security
         */
        private static MybatisSqlSessionFactoryUtil instance = new MybatisSqlSessionFactoryUtil();
    }
    
    public static MybatisSqlSessionFactoryUtil getInstance(){
        return MybatisSqlSessionFactoryUtilHolder.instance;
    }
    
    
    public SqlSession getSqlSession() {
        if(sqlSessionFactory == null){
        	return null;
        }
        
        return sqlSessionFactory.openSession();
    }
    
    
	
	

}
