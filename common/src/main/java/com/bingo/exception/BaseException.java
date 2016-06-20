package com.bingo.exception;

/**
 * Base com.bingo.exception, the com.bingo.exception of this class or its sub class should be thrown
 * explicitly which means we could by pass the trace log for them
 * */
public class BaseException extends RuntimeException {

	public BaseException(String message) {
		super(message);
	}

}
