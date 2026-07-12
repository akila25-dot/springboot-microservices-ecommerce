package com.authservice.demo.dto;

// Java record for immutable DTO
public record UserTokenResponse(String accessToken, String tokenType, long expiresInSeconds) {}
