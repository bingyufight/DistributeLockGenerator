package com.base.distribute.lock.generator;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;

import com.base.distribute.lock.generator.core.DistributeLockType;

public class RedisDistributeLockTest  {
	
//	@Test
//	public void testRedisDistributeLock() throws InterruptedException{
//		ExecutorService pool = Executors.newFixedThreadPool(5);
//		
//			TestTask testTask1 = new TestTask("TestTaskRedis",DistributeLockType.REDIS);
//			Future<Long> future1= pool.submit(testTask1);
////			TestTask testTask2 = new TestTask("TestTask2");
////			Future<Long> future2= pool.submit(testTask2);
//			long costTime1 = 0;
//		//	long costTime2 = 0;
//		    try {
//		    	  costTime1 = future1.get();
//		    	//  costTime2 = future2.get();
//			  System.out.println("TestTask1" +" execute" + costTime1/1000.0 +"s");
////			  System.out.println("TestTask2"  +" execute" + costTime2/1000 +"s");
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (ExecutionException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		    
////		    
//	    Thread.sleep(60 * 1000);
//		    
//		    System.out.println("done");
//	}
	
	
	@Test
	public void multiTestRedisDistributeLock() throws InterruptedException{

		ExecutorService pool = Executors.newFixedThreadPool(5);
		TestTask testTask1 = new TestTask("multiTestRedisDistributeLock1",DistributeLockType.REDIS);
		Future<Long> future1= pool.submit(testTask1);
		TestTask testTask2 = new TestTask("multiTestRedisDistributeLock2",DistributeLockType.REDIS);
		Future<Long> future2= pool.submit(testTask2);
		
		TestTask testTask3 = new TestTask("multiTestRedisDistributeLock3",DistributeLockType.REDIS);
		Future<Long> future3= pool.submit(testTask3);
		
		TestTask testTask4 = new TestTask("multiTestRedisDistributeLock4",DistributeLockType.REDIS);
		Future<Long> future4= pool.submit(testTask4);
		
		TestTask testTask5 = new TestTask("multiTestRedisDistributeLock5",DistributeLockType.REDIS);
		Future<Long> future5= pool.submit(testTask5);
		
		long costTime1 = 0;
		long costTime2 = 0;
		long costTime3 = 0;
		long costTime4 = 0;
		long costTime5 = 0;
	    try {
	    	  costTime1 = future1.get();
	    	  costTime2= future2.get();
	    	  costTime3 = future3.get();
	    	  costTime4 = future4.get();
	    	  costTime5 = future5.get();
  
		  System.out.println("TestTask1" +" execute" + costTime1/1000.0 +"s");
		  System.out.println("TestTask2" +" execute" + costTime2/1000.0 +"s");
		  System.out.println("TestTask3" +" execute" + costTime3/1000.0 +"s");
		  System.out.println("TestTask4" +" execute" + costTime4/1000.0 +"s");
		  System.out.println("TestTask5" +" execute" + costTime5/1000.0 +"s");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
//	    
   
	    
	    System.out.println("done");
	}
	
}
