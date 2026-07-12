package com.authservice.demo.exception;

/**
 * Exception for user account lifecycle failures (registration, login, role validation).
 */
public class UserAuthException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private final AuthErrorCode errorCode;
    private final int httpStatus;

    public UserAuthException(AuthErrorCode errorCode, String message, int httpStatus) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

    public AuthErrorCode getErrorCode() { return errorCode; }
    public int getHttpStatus() { return httpStatus; }
}
