package com.productservice.demo.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

/**
 * DTO for creating or updating a Product using Java record.
 */
public record ProductRequest(
        @NotBlank(message = "Product name is required")
        String productName,

        @Size(max = 500, message = "Description must be at most 500 characters")
        String description,

        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.01", inclusive = true, message = "Price must be greater than zero")
        BigDecimal price,

        @NotNull(message = "Stock is required")
        @Min(value = 0, message = "Stock cannot be negative")
        Integer stock
) {}
