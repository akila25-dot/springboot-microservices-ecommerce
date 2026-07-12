package com.authservice.demo.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "auth.jwt")
public record JwtProperties(String secret, int expiryMinutes) { }


