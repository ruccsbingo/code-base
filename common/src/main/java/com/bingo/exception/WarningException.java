/**
 * 
 */
package com.bingo.exception;

/**
 * @author liubo
 * @email liubo@yidian-inc.com
 * 
 */
public class WarningException extends RuntimeException {

	public WarningException(String message) {
		super(message);
	}

	public WarningException(String message, Throwable cause) {
		super(message, cause);
	}

}
