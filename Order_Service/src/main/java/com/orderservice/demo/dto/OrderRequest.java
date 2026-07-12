package com.orderservice.demo.dto;

import java.math.BigDecimal;

public record OrderRequest(
        Long productId,        // 👈 added
        int quantity,
        BigDecimal price,
        String customerId
) {}
