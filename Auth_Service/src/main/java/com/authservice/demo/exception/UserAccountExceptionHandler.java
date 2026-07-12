package com.authservice.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.Map;

/**
 * Exception handler for user account and authentication errors.
 * Converts exceptions into structured JSON error responses.
 */
@ControllerAdvice
public class UserAccountExceptionHandler {

    // Handle custom UserAuthException
    @ExceptionHandler(UserAuthException.class)
    public ResponseEntity<?> handleUserAuthException(UserAuthException ex) {
    	ex.printStackTrace(); 
        return ResponseEntity.status(ex.getHttpStatus()).body(Map.of(
            "type", "about:blank",
            "title", ex.getErrorCode().name(),
            "status", ex.getHttpStatus(),
            "detail", ex.getMessage(),
            "timestamp", Instant.now().toString()
        ));
    }

    // Handle generic exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
            "type", "about:blank",
            "title", AuthErrorCode.INTERNAL_AUTH_ERROR.name(),
            "status", HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "detail", "Unexpected error occurred during user authentication process",
            "timestamp", Instant.now().toString()
        ));
    }
}
