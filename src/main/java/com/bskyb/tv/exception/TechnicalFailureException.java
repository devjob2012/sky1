package com.bskyb.tv.exception;

public class TechnicalFailureException extends Exception {

	private static final long serialVersionUID = 1L;

	public TechnicalFailureException() {
		super();
	}

	public TechnicalFailureException(String message) {
		super(message);
	}

	public TechnicalFailureException(String message, Throwable cause) {
		super(message, cause);
	}

	public TechnicalFailureException(Throwable cause) {
		super(cause);
	}
}
