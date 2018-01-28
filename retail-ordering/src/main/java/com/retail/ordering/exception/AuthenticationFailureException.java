package com.retail.ordering.exception;

public class AuthenticationFailureException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AuthenticationFailureException(final String message) {
		super(message);
	}

}
