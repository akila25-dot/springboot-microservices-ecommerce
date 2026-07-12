
/*package com.orderservice.demo.service;

import com.orderservice.demo.dto.OrderRequest;
import com.orderservice.demo.dto.OrderResponse;
import com.orderservice.demo.dto.ProductResponse;
import com.orderservice.demo.exception.BadRequestException;
import com.orderservice.demo.exception.NotFoundException;
import com.orderservice.demo.model.Order;
import com.orderservice.demo.model.OrderStatus;
import com.orderservice.demo.repository.OrderRepository;
import com.orderservice.demo.web.ProductClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;

    @Override
    @CircuitBreaker(name = "productService", fallbackMethod = "createOrderFallback")
    public OrderResponse createOrder(OrderRequest request) {
        log.info("Creating order for productId: {}", request.productId());

        var product = productClient.getProductById(request.productId());

        if (request.quantity() > product.stock()) {
            throw new BadRequestException("Insufficient stock. Available: " + product.stock());
        }

        BigDecimal total = product.price().multiply(BigDecimal.valueOf(request.quantity()));

        var order = Order.builder()
                .productId(product.id())
                .productName(product.productName())
                .quantity(request.quantity())
                .price(product.price())
                .totalAmount(total)
                .status(OrderStatus.CREATED)
                .customerId(request.customerId())
                .build();

        var saved = orderRepository.save(order);
        return mapToResponse(saved, product);
    }

    // ✅ Fallback method triggered when Product Service fails or circuit breaker opens
    public OrderResponse createOrderFallback(OrderRequest request, Throwable t) {
        log.error("Fallback triggered for createOrder due to Product Service failure", t);

        var order = Order.builder()
                .productId(request.productId())
                .productName("Unknown Product")
                .quantity(request.quantity())
                .price(BigDecimal.ZERO)
                .totalAmount(BigDecimal.ZERO)
                .status(OrderStatus.PENDING_STOCK_CHECK)
                .customerId(request.customerId())
                .build();

        var saved = orderRepository.save(order);
     // ✅ Create a fallback ProductResponse with message
        ProductClient.ProductResponse fallbackProduct = new ProductClient.ProductResponse(
                request.productId(),
                "Unknown Product",
                "Product Service is currently unavailable. Could not fetch product with id " + request.productId(),
                BigDecimal.ZERO,
                0
        );
        return mapToResponse(saved, fallbackProduct);
    }

    @Override
    public OrderResponse getOrder(Long id) {
        var order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found: " + id));

        ProductClient.ProductResponse product;
        try {
            product = productClient.getProductById(order.getProductId());
        } catch (Exception e) {
            log.warn("Fallback triggered: Product Service unavailable for id {}", order.getProductId());
            // ✅ Build a fallback product response instead of null
            product = new ProductClient.ProductResponse(
                    order.getProductId(),
                    "Unknown Product",
                    "Product Service is currently unavailable. Could not fetch product with id " + order.getProductId(),
                    BigDecimal.ZERO,
                    0
            );
        }

        return mapToResponse(order, product);
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(order -> {
                    ProductClient.ProductResponse product;
                    try {
                        product = productClient.getProductById(order.getProductId());
                    } catch (Exception e) {
                        log.warn("Fallback triggered: Product Service unavailable for id {}", order.getProductId());
                        // ✅ Build a fallback product response instead of null
                        product = new ProductClient.ProductResponse(
                                order.getProductId(),
                                "Unknown Product",
                                "Product Service is currently unavailable. Could not fetch product with id " + order.getProductId(),
                                BigDecimal.ZERO,
                                0
                        );
                    }
                    return mapToResponse(order, product);
                })
                .collect(Collectors.toList());
    }


    @Override
    public OrderStatus getOrderStatus(Long id) {
        var order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found: " + id));
        return order.getStatus();
    }

    @Override
    public OrderResponse updateOrderStatus(Long id, OrderStatus status) {
        var order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found: " + id));
        order.setStatus(status);
        var saved = orderRepository.save(order);
        return mapToResponse(saved, null);
    }

    @Override
    public void cancelOrder(Long id) {
        var order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found: " + id));
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    private OrderResponse mapToResponse(Order order, ProductClient.ProductResponse product) {
        var productResponse = product == null ? null : new ProductResponse(
                product.id(),
                product.productName(),
                product.description(),
                product.price(),
                product.stock()
        );

        return new OrderResponse(
                order.getId(),
                order.getProductName(),
                order.getQuantity(),
                order.getPrice(),
                order.getTotalAmount(),
                order.getStatus().name(),
                order.getCustomerId(),
                productResponse
        );
    }
}
*/

/*package com.orderservice.demo.service;

import com.orderservice.demo.dto.OrderRequest;
import com.orderservice.demo.dto.OrderResponse;
import com.orderservice.demo.dto.ProductResponse;
import com.orderservice.demo.exception.BadRequestException;
import com.orderservice.demo.exception.NotFoundException;
import com.orderservice.demo.model.Order;
import com.orderservice.demo.model.OrderStatus;
import com.orderservice.demo.repository.OrderRepository;
import com.orderservice.demo.web.ProductClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;

    // ---------------- CREATE ORDER ----------------
    @Override
    @CircuitBreaker(name = "productService", fallbackMethod = "createOrderFallback")
    public OrderResponse createOrder(OrderRequest request) {
        log.info("Creating order for productId: {}", request.productId());

        var product = productClient.getProductById(request.productId());

        if (request.quantity() > product.stock()) {
            throw new BadRequestException("Insufficient stock. Available: " + product.stock());
        }

        BigDecimal total = product.price().multiply(BigDecimal.valueOf(request.quantity()));

        var order = Order.builder()
                .productId(product.id())
                .productName(product.productName())
                .quantity(request.quantity())
                .price(product.price())
                .totalAmount(total)
                .status(OrderStatus.CREATED)
                .customerId(request.customerId())
                .build();

        var saved = orderRepository.save(order);
        return mapToResponse(saved, product);
    }

    public OrderResponse createOrderFallback(OrderRequest request, Throwable t) {
        log.error("Fallback triggered for createOrder due to Product Service failure", t);

        var order = Order.builder()
                .productId(request.productId())
                .productName("Unknown Product")
                .quantity(request.quantity())
                .price(BigDecimal.ZERO)
                .totalAmount(BigDecimal.ZERO)
                .status(OrderStatus.PENDING_STOCK_CHECK)
                .customerId(request.customerId())
                .build();

        var saved = orderRepository.save(order);
        return mapToResponse(saved,
                buildFallbackProduct(request.productId(),
                        "Could not fetch product with id " + request.productId()));
    }

    // ---------------- GET ORDER BY ID ----------------
    @Override
    @CircuitBreaker(name = "productService", fallbackMethod = "getOrderFallback")
    public OrderResponse getOrder(Long id) {
        var order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found: " + id));

        var product = productClient.getProductById(order.getProductId());
        return mapToResponse(order, product);
    }

    public OrderResponse getOrderFallback(Long id, Throwable t) {
        log.warn("Fallback triggered for getOrder due to Product Service failure", t);

        var order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found: " + id));

        return mapToResponse(order,
                buildFallbackProduct(order.getProductId(),
                        "Could not fetch product with id " + order.getProductId()));
    }

    // ---------------- GET ALL ORDERS ----------------
    @Override
    @CircuitBreaker(name = "productService", fallbackMethod = "getAllOrdersFallback")
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(order -> {
                    var product = productClient.getProductById(order.getProductId());
                    return mapToResponse(order, product);
                })
                .collect(Collectors.toList());
    }

    public List<OrderResponse> getAllOrdersFallback(Throwable t) {
        log.warn("Fallback triggered for getAllOrders due to Product Service failure", t);

        return orderRepository.findAll().stream()
                .map(order -> mapToResponse(order,
                        buildFallbackProduct(order.getProductId(),
                                "Could not fetch product with id " + order.getProductId())))
                .collect(Collectors.toList());
    }

    // ---------------- OTHER METHODS ----------------
    @Override
    public OrderStatus getOrderStatus(Long id) {
        var order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found: " + id));
        return order.getStatus();
    }

    @Override
    public OrderResponse updateOrderStatus(Long id, OrderStatus status) {
        var order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found: " + id));
        order.setStatus(status);
        var saved = orderRepository.save(order);
        return mapToResponse(saved, null); // no circuit breaker here
    }

    @Override
    public void cancelOrder(Long id) {
        var order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found: " + id));
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    // ---------------- HELPERS ----------------
    private OrderResponse mapToResponse(Order order, ProductClient.ProductResponse product) {
        var productResponse = product == null ? null : new ProductResponse(
                product.id(),
                product.productName(),
                product.description(),
                product.price(),
                product.stock()
        );

        return new OrderResponse(
                order.getId(),
                order.getProductName(),
                order.getQuantity(),
                order.getPrice(),
                order.getTotalAmount(),
                order.getStatus().name(),
                order.getCustomerId(),
                productResponse
        );
    }

    private ProductClient.ProductResponse buildFallbackProduct(Long productId, String contextMessage) {
        return new ProductClient.ProductResponse(
                productId,
                "Unknown Product",
                "Product Service is currently unavailable. " + contextMessage,
                BigDecimal.ZERO,
                0
        );
    }
}
*/


package com.orderservice.demo.service;

import com.orderservice.demo.dto.OrderRequest;
import com.orderservice.demo.dto.OrderResponse;
import com.orderservice.demo.dto.ProductResponse;
import com.orderservice.demo.exception.BadRequestException;
import com.orderservice.demo.exception.NotFoundException;
import com.orderservice.demo.model.Order;
import com.orderservice.demo.model.OrderStatus;
import com.orderservice.demo.repository.OrderRepository;
import com.orderservice.demo.web.ProductClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;

    // ---------------- CREATE ORDER ----------------
    @Override
    @CircuitBreaker(name = "productService", fallbackMethod = "createOrderFallback")
    public OrderResponse createOrder(OrderRequest request) {
        log.info("Creating order for productId: {}", request.productId());

        var product = productClient.getProductById(request.productId());

        if (request.quantity() > product.stock()) {
            throw new BadRequestException("Insufficient stock. Available: " + product.stock());
        }

        BigDecimal total = product.price().multiply(BigDecimal.valueOf(request.quantity()));

        var order = Order.builder()
                .productId(product.id())
                .productName(product.productName())
                .quantity(request.quantity())
                .price(product.price())
                .totalAmount(total)
                .status(OrderStatus.CREATED)
                .customerId(request.customerId())
                .build();

        var saved = orderRepository.save(order);
        return mapToResponse(saved, product);
    }

    public OrderResponse createOrderFallback(OrderRequest request, Throwable t) {
        log.error("Fallback triggered for createOrder due to Product Service failure", t);

        var order = Order.builder()
                .productId(request.productId())
                .productName("Unknown Product")
                .quantity(request.quantity())
                .price(BigDecimal.ZERO)
                .totalAmount(BigDecimal.ZERO)
                .status(OrderStatus.PENDING_STOCK_CHECK)
                .customerId(request.customerId())
                .build();

        var saved = orderRepository.save(order);
        return mapToResponse(saved,
                buildFallbackProduct(request.productId(),
                        "Could not fetch product with id " + request.productId()));
    }

    // ---------------- GET ORDER BY ID ----------------
    @Override
    @CircuitBreaker(name = "productService", fallbackMethod = "getOrderFallback")
    public OrderResponse getOrder(Long id) {
        var order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found: " + id));

        var product = productClient.getProductById(order.getProductId());
        return mapToResponse(order, product);
    }

    public OrderResponse getOrderFallback(Long id, Throwable t) {
        log.warn("Fallback triggered for getOrder due to Product Service failure", t);

        var order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found: " + id));

        return mapToResponse(order,
                buildFallbackProduct(order.getProductId(),
                        "Could not fetch product with id " + order.getProductId()));
    }

   
 // ---------------- GET ALL ORDERS ----------------
  


    @Override
    @CircuitBreaker(name = "productService", fallbackMethod = "getAllOrdersFallback")
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(order -> {
                    ProductClient.ProductResponse product = null;
                    try {
                        product = productClient.getProductById(order.getProductId());
                    } catch (Exception e) {
                        log.warn("Product not found in Product Service for id: {}", order.getProductId(), e);
                    }
                    return mapToResponse(order, product);
                })
                .collect(Collectors.toList());
    }

    public List<OrderResponse> getAllOrdersFallback(Throwable t) {
        log.warn("Fallback triggered for getAllOrders due to Product Service failure", t);

        return orderRepository.findAll().stream()
                .map(order -> {
                    // Build a synthetic product when Product Service is unavailable
                    var fallbackProduct = buildFallbackProduct(order.getProductId(),
                            "Could not fetch product with id " + order.getProductId());
                    return mapToResponse(order, fallbackProduct);
                })
                .collect(Collectors.toList());
    }

    
    // ---------------- OTHER METHODS ----------------
    @Override
    public OrderStatus getOrderStatus(Long id) {
        var order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found: " + id));
        return order.getStatus();
    }

    @Override
    public OrderResponse updateOrderStatus(Long id, OrderStatus status) {
        var order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found: " + id));
        order.setStatus(status);
        var saved = orderRepository.save(order);
        return mapToResponse(saved, null); // no circuit breaker here
    }

    @Override
    public void cancelOrder(Long id) {
        var order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found: " + id));
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    // ---------------- HELPERS ----------------
    private OrderResponse mapToResponse(Order order, ProductClient.ProductResponse product) {
        var productResponse = product == null ? null : new ProductResponse(
                product.id(),
                product.productName(),
                product.description(),
                product.price(),
                product.stock()
        );

        return new OrderResponse(
                order.getId(),
                order.getProductName(),
                order.getQuantity(),
                order.getPrice(),
                order.getTotalAmount(),
                order.getStatus().name(),
                order.getCustomerId(),
                productResponse
        );
    }

    private ProductClient.ProductResponse buildFallbackProduct(Long productId, String contextMessage) {
        return new ProductClient.ProductResponse(
                productId,
                "Unknown Product",
                "Product Service is currently unavailable. " + contextMessage,
                BigDecimal.ZERO,
                0
        );
    }
}
