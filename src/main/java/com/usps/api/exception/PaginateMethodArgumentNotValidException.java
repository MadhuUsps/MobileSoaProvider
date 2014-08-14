package com.usps.api.exception;

public class PaginateMethodArgumentNotValidException extends Exception {
	private static final long serialVersionUID = 5405074780005819842L;

	public PaginateMethodArgumentNotValidException() {
		super();
	}

	public PaginateMethodArgumentNotValidException(String message) {
		super(message);
	}
}
