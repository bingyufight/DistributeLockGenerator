package com.base.distribute.lock.generator.async;

import java.util.concurrent.ExecutorService;

import com.base.distribute.lock.generator.constant.DLGConstants;

/**
 * 
 * @author yubing
 *
 */
public class AsyncThreadPool {
	
	private ExecutorService service = null;
    private AsyncThreadPool(){
        service = new CustomThreadPoolExecutor(DLGConstants.THREAD_POOL_SIZE);
    }
    
    
    private static class AsyncThreadPoolHolder{
     
        private static AsyncThreadPool instance = new AsyncThreadPool();
    }
    
    public static AsyncThreadPool getInstance(){
        return AsyncThreadPoolHolder.instance;
    }
    
    public void submitTask(Runnable task) {
        service.execute(task);
    }

}
