package com.bskyb.tv.exception;

public class TitleNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public TitleNotFoundException() {
		super();
	}

	public TitleNotFoundException(String message) {
		super(message);
	}

	public TitleNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public TitleNotFoundException(Throwable cause) {
		super(cause);
	}
}
