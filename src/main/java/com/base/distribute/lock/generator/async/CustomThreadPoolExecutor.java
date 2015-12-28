package com.base.distribute.lock.generator.async;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author yubing
 *
 */
public class CustomThreadPoolExecutor extends ThreadPoolExecutor {
	
	private static Logger logger = LoggerFactory.getLogger(CustomThreadPoolExecutor.class);
	
	private static ThreadFactory threadFactory = Executors.defaultThreadFactory(); 
	
	private static CustomRejectedExecutionHandlerImpl rejectedExecutionHandler = new CustomRejectedExecutionHandlerImpl();
	
	
	public CustomThreadPoolExecutor(int nThreadPoolSize){
		this(nThreadPoolSize,nThreadPoolSize,0L,TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>());
		logger.info("init customThreadPoolExecutor,nThreadPoolSize:{}",nThreadPoolSize);
	}

	public CustomThreadPoolExecutor(int corePoolSize, int maximumPoolSize,
			long keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue) {
		
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
				threadFactory, rejectedExecutionHandler);
	}
	
	

}
