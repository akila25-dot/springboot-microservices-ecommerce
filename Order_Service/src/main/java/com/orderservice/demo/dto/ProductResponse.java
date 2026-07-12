package com.orderservice.demo.dto;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String productName,
        String description,
        BigDecimal price,
        int stock
) {}
