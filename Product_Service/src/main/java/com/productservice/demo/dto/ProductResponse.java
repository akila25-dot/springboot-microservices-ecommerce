package com.productservice.demo.dto;


import java.math.BigDecimal;

/**
 * DTO for returning Product details using Java record.
 */
public record ProductResponse(
        Long id,
        String productName,
        String description,
        BigDecimal price,
        int stock
) {}
