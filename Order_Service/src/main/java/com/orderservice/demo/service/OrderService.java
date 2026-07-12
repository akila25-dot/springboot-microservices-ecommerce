package com.orderservice.demo.service;

import com.orderservice.demo.dto.OrderRequest;
import com.orderservice.demo.dto.OrderResponse;
import com.orderservice.demo.model.OrderStatus;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderRequest request);
    OrderResponse getOrder(Long id);
    List<OrderResponse> getAllOrders();

    OrderStatus getOrderStatus(Long id);
    OrderResponse updateOrderStatus(Long id, OrderStatus status);
    void cancelOrder(Long id);
}
