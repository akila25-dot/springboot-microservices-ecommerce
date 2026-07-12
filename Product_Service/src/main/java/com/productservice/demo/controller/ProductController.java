package com.productservice.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.productservice.demo.dto.ProductResponse;
import com.productservice.demo.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Accessible by ROLE_USER
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    // Accessible by ROLE_USER
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }
    // ✅ New endpoint: fetch product by name
    @GetMapping("/by-name/{name}")
    public ResponseEntity<ProductResponse> getProductByName(@PathVariable String name) {
        return ResponseEntity.ok(productService.getProductByName(name));
        
    }
   
    
    
    
}
