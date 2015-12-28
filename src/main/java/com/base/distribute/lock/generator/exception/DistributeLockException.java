package com.base.distribute.lock.generator.exception;
/**
 * 
 * @author yubing
 *
 */
public class DistributeLockException  extends Exception {

	private static final long serialVersionUID = -9204081047030646442L;
	
	public DistributeLockException(String message){
		super(message);
	}
	
	public DistributeLockException(String message, Throwable cause) {
        super(message,cause);
    }
	
	

}
