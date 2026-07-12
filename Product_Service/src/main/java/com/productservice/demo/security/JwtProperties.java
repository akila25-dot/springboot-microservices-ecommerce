package com.productservice.demo.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "product.jwt")
public record JwtProperties(String secret, int expiryMinutes) { }


