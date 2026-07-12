package com.productservice.demo.exception;

/**
 * Thrown when a requested resource (e.g., Product) is not found.
 */
public class NotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
    public NotFoundException(String message) {
        super(message);
    }
}
