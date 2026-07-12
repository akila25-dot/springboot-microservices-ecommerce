package com.orderservice.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    private String productName;
    private int quantity;
    private BigDecimal price;
    
    @Column(name = "total_amount", columnDefinition = "DOUBLE NOT NULL")
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)   // store enum name in DB
    private OrderStatus status;    // use enum instead of String

    private String customerId;
}
