package com.orderservice.demo.model;

public enum OrderStatus {
    CREATED,             // order placed successfully
    PENDING_STOCK_CHECK, // fallback when product service unavailable
    CONFIRMED,           // stock validated and payment confirmed
    PROCESSING,          // preparing/packing order
    SHIPPED,             // dispatched to courier
    DELIVERED,           // delivered to customer
    CANCELLED,           // cancelled by user/admin
    RETURNED             // returned after delivery
}
/*package com.orderservice.demo.model;

public enum OrderStatus {
    CREATED,
    PENDING_STOCK_CHECK,
    CANCELLED,
    CONFIRMED
}*/
