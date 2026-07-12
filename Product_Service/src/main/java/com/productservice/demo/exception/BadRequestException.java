package com.productservice.demo.exception;

/**
 * Thrown when a request contains invalid data.
 */
public class BadRequestException extends RuntimeException {
	private static final long serialVersionUID = 1L;
    public BadRequestException(String message) {
        super(message);
    }
}
