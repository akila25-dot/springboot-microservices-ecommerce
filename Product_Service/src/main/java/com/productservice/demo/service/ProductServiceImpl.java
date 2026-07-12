package com.productservice.demo.service;

import com.productservice.demo.dto.ProductRequest;
import com.productservice.demo.dto.ProductResponse;
import com.productservice.demo.exception.BadRequestException;
import com.productservice.demo.exception.NotFoundException;
import com.productservice.demo.model.Product;
import com.productservice.demo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Transactional
    @Override
    public ProductResponse createProduct(ProductRequest request) {
        validateProduct(request);
        Product product = Product.builder()
                .productName(request.productName())
                .description(request.description())
                .price(request.price())
                .stock(request.stock())
                .build();
        return toResponse(productRepository.save(product));
    }

    @Transactional(readOnly = true)
    @Override
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found: " + id));
        return toResponse(product);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    @Override
    public ProductResponse getProductByName(String productName) {
        Product product = productRepository.findByProductName(productName)
                .orElseThrow(() -> new NotFoundException("Product not found: " + productName));
        return toResponse(product);
    }

    @Transactional
    @Override
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        validateProduct(request);
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found: " + id));

        product.setProductName(request.productName());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setStock(request.stock());

        return toResponse(productRepository.save(product));
    }

    @Transactional
    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new NotFoundException("Product not found: " + id);
        }
        productRepository.deleteById(id);
    }

    private void validateProduct(ProductRequest request) {
        if (request.productName() == null || request.productName().isBlank()) {
            throw new BadRequestException("Product name is required");
        }
        if (request.price() == null || request.price().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Price must be greater than zero");
        }
        if (request.stock() == null || request.stock() < 0) {
            throw new BadRequestException("Stock must be provided and cannot be negative");
        }
    }

    private ProductResponse toResponse(Product p) {
        return new ProductResponse(
                p.getId(),
                p.getProductName(),
                p.getDescription(),
                p.getPrice(),
                p.getStock()
        );
    }
}
