package com.orderservice.demo.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "order.jwt")
public record JwtProperties(String secret, int expiryMinutes) { }


