package com.gxuwz.medical.exception;
/**
 * 
 * @author 演示
 *
 */
public class DbException extends Exception {

	public DbException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public DbException(String message) {
		super(message);
		
	}
	

}
