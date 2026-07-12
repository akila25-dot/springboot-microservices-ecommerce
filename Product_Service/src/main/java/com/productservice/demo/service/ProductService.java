package com.productservice.demo.service;

import com.productservice.demo.dto.ProductRequest;
import com.productservice.demo.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(ProductRequest request);
    ProductResponse getProductById(Long id);
    List<ProductResponse> getAllProducts();
    ProductResponse updateProduct(Long id, ProductRequest request);
    void deleteProduct(Long id);

    // 👇 keep this if you want product lookup by name
    ProductResponse getProductByName(String productName);
}
