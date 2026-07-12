package com.orderservice.demo.controller;

import com.orderservice.demo.dto.OrderResponse;
import com.orderservice.demo.model.OrderStatus;
import com.orderservice.demo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/admin/orders")
@RequiredArgsConstructor
public class AdminController {

    private final OrderService orderService;

    // --- Get all orders ---
    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    // --- Get order by ID ---
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrder(id));
    }

    // --- Update order status ---
    @PutMapping("/{id}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(@PathVariable Long id,
                                                           @RequestParam OrderStatus status) {
        return ResponseEntity.ok(orderService.updateOrderStatus(id, status));
    }

    // --- Cancel order ---
    /*@DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);
        return ResponseEntity.noContent().build();*/
        
        @DeleteMapping("/{id}")
        public ResponseEntity<Map<String, String>> cancelOrder(@PathVariable Long id) {
            orderService.cancelOrder(id);
            return ResponseEntity.ok(Map.of("message", "Order cancelled successfully"));
        }

    }

