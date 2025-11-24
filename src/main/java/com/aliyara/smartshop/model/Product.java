package com.aliyara.smartshop.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "unit_price", nullable = false)
    private double unitPrice;

    @Column(name = "stock", nullable = false)
    private int stock = 0;

    @OneToMany(mappedBy = "product_id", fetch = FetchType.LAZY)
    private List<OrderItem> orderItems;
}
