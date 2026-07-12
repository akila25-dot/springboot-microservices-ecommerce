package com.orderservice.demo.controller;

import com.orderservice.demo.dto.OrderRequest;
import com.orderservice.demo.dto.OrderResponse;
import com.orderservice.demo.model.OrderStatus;
import com.orderservice.demo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // --- Create Order (USER only) ---
    @PostMapping("/create")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest request) {
        return ResponseEntity.ok(orderService.createOrder(request));
    }

    // --- Get Order by ID ---
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrder(id));
    }

    // --- Get All Orders ---
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    // --- Get Order Status ---
    @GetMapping("/{id}/status")
    public ResponseEntity<OrderStatus> getOrderStatus(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderStatus(id));
    }
    
 // ✅ Cancel order endpoint for users
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);
        return ResponseEntity.ok(Map.of("message", "Order cancelled successfully"));
    }

}
