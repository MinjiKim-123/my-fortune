package com.fortune.exception;

public class FortuneException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public FortuneException(String message, Throwable e) {
		super(message, e);
	}
	
	public FortuneException(String message) {
		super(message);
	}
	
	public FortuneException(Throwable e) {
		super(e);
	}
	
}
