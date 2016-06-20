/**
 * 
 */
package com.bingo.exception;

/**
 * @author liubo
 * @email liubo@yidian-inc.com
 * 
 */
public class InvalidEnumArgumentException extends RuntimeException {

	public InvalidEnumArgumentException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidEnumArgumentException(String message) {
		super(message);
	}

}
