package com.authservice.demo.exception;

/**
 * Enum representing error codes for authentication and user account lifecycle failures.
 */
public enum AuthErrorCode {
    USER_NOT_FOUND,
    INVALID_CREDENTIALS,
    TOKEN_GENERATION_FAILED,
    REGISTRATION_FAILED,
    ROLE_VALIDATION_FAILED,
    INTERNAL_AUTH_ERROR
}
