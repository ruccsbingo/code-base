package com.bingo.exception;

/**
 * copied, liubo
 */

public class SigConvertException extends RuntimeException {

	public SigConvertException() {
	}

	public SigConvertException(String message) {
		super(message);
	}

	public SigConvertException(String message, Throwable cause) {
		super(message, cause);
	}

	public SigConvertException(Throwable cause) {
		super(cause);
	}
}
