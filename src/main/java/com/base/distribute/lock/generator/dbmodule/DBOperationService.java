package com.base.distribute.lock.generator.dbmodule;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.base.distribute.lock.generator.dbmodule.pojo.DistributeLockLogInfo;

/**
 * 
 * @author yubing
 *
 */
public class DBOperationService {
	
	private static Logger logger = LoggerFactory.getLogger(DBOperationService.class);
	public static int saveDistributeLockLogInfo(DistributeLockLogInfo distributeLockLogInfo){
		logger.info("start excuting saveDistributeLockLogInfo,distributeLockLogInfo:{}",distributeLockLogInfo);
		
		SqlSession session = MybatisSqlSessionFactoryUtil.getInstance().getSqlSession();
		try{
			if(session != null){
				DistributeLockLogInfoMapper distributeLockLogInfoMapper = session.getMapper(DistributeLockLogInfoMapper.class);
				int result = distributeLockLogInfoMapper.saveDistributeLockLogInfo(distributeLockLogInfo);
				session.commit();
				return result;
			}
		} catch(Exception e){
			if(session != null){
				session.rollback();
			}
			logger.error("DBOperationService execute saveDistributeLockLogInfo occur error ----",e);
		}
		finally{
			if(session != null){
				session.close();
			}
		}
		
		return 0;
		
	}
	
	public static int updateDistributeLockLogInfo(DistributeLockLogInfo distributeLockLogInfo){
		logger.info("start excuting updateDistributeLockLogInfo,distributeLockLogInfo:{}",distributeLockLogInfo);
		SqlSession session = MybatisSqlSessionFactoryUtil.getInstance().getSqlSession();
		try{
			if(session != null){
				DistributeLockLogInfoMapper distributeLockLogInfoMapper = session.getMapper(DistributeLockLogInfoMapper.class);
				int result = distributeLockLogInfoMapper.updateDistributeLockLogInfo(distributeLockLogInfo);
				session.commit();
				return result;
			}
		} catch(Exception e){
			if(session != null){
				session.rollback();
			}
			logger.error("DBOperationService execute updateDistributeLockLogInfo occur error ----",e);
		}
		finally{
			if(session != null){
				session.close();
			}
		}
		
		return 0;
	}
	
	public static List<DistributeLockLogInfo> getDistributeLockLogInfoByUUID(String lockClientUuid){
		logger.info("start excuting getDistributeLockLogInfoByUUID,lockClientUuid:{}",lockClientUuid);
		
		SqlSession session = MybatisSqlSessionFactoryUtil.getInstance().getSqlSession();
		try{
			if(session != null){
				DistributeLockLogInfoMapper distributeLockLogInfoMapper = session.getMapper(DistributeLockLogInfoMapper.class);
				return distributeLockLogInfoMapper.getDistributeLockLogInfoByUUID(lockClientUuid);
			}
		} catch(Exception e){
			logger.error("start excuting getDistributeLockLogInfoByUUID occur error ----",e);
		}
		finally{
			if(session != null){
				session.close();
			}
		}
		
		return null;
	}

}
