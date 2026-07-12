package com.orderservice.demo.web;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

import java.math.BigDecimal;


@FeignClient(
   name = "product-service",
    url = "${product.service.base-url}",   // e.g. http://localhost:8083
    configuration = com.orderservice.demo.config.FeignAuthConfig.class
    
)
public interface ProductClient {

    // --- USER ENDPOINTS ---

    // Fetch a single product by ID
    @GetMapping("/api/v1/product/{id}")
    ProductResponse getProductById(@PathVariable Long id);

    // Fetch all products
    @GetMapping("/api/v1/product")
    List<ProductResponse> getAllProducts();

    // Fetch a product by name
    @GetMapping("/api/v1/product/by-name/{productName}")
    ProductResponse getProductByName(@PathVariable String productName);

    // --- ADMIN ENDPOINTS ---

    // Create a new product
    @PostMapping("/api/v1/admin/product/create")
    ProductResponse createProduct(@RequestBody ProductRequest request);
/*
    // Update an existing product
    @PutMapping("/api/v1/admin/product/{id}")
    ProductResponse updateProduct(@PathVariable Long id,
                                  @RequestBody ProductRequest request);

    // Delete a product
    @DeleteMapping("/api/v1/admin/product/{id}")
    void deleteProduct(@PathVariable Long id);

    // --- DTOs must match Product Service JSON ---
*/
    // ✅ Response DTO (field renamed to productName)
    record ProductResponse(Long id, String productName, String description, BigDecimal price, int stock) {}

    // Request DTO for creation & update
    record ProductRequest(String productName, BigDecimal price, int stock, String description) {}
}
