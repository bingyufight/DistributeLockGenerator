package com.base.distribute.lock.generator;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.base.distribute.lock.generator.core.DistributeLockManager;
import com.base.distribute.lock.generator.core.DistributeLockType;
import com.base.distribute.lock.generator.core.IDistributeLock;



public class TestTask implements Callable<Long> {


	private String taskName;
	
	private DistributeLockType distributeLockType;
	
	public TestTask(String taskName,DistributeLockType distributeLockType){
		this.taskName = taskName;
		this.distributeLockType = distributeLockType;
	}
	public Long call() throws Exception {
		System.out.println(String.format("{%s} start to excute-----",taskName));
		long beginTime = System.currentTimeMillis();
		
		IDistributeLock distributeLock = DistributeLockManager.createDistributeLock(distributeLockType);
		
		try{
			boolean result = distributeLock.acquireLock();
			System.out.println("result=" + result);
			if(result){
				System.out.println(taskName + "has get this distributeLock");
				
				Thread.sleep(1 * 1000);
			}
		}catch(Exception e){
			System.out.println("occur error");
		}finally{
			if(distributeLock != null){
				distributeLock.releaseLock();
			}
			
		}
		
		long afterTime = System.currentTimeMillis();
		return afterTime-beginTime;
	}

}
