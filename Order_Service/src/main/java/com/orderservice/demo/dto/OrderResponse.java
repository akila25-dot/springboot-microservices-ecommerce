package com.orderservice.demo.dto;

import java.math.BigDecimal;

public record OrderResponse(
        Long id,
        String productName,
        int quantity,
        BigDecimal price,
        BigDecimal totalAmount,
        String status,
        String customerId,
        ProductResponse product   // 👈 nested product details
) {}
