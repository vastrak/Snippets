package com.vastrak.validate;

/**
 * Thrown to indicate that a field has been passed null
 * 
 * @author Christian
 *
 */

public class RequiredFieldException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RequiredFieldException(String message) {
		super(message);
	}
}
