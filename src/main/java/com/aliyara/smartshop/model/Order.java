package com.aliyara.smartshop.model;

import com.aliyara.smartshop.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, name = "order_date")
    private LocalDateTime orderDate = LocalDateTime.now();

    @Column(nullable = false)
    private double subtotal = 0.0;

    private double discount = 0.0;

    @Value("${vat.value}")
    @Column(nullable = false)
    private double VAT;

    @Column(nullable = false)
    private double total = 0;

    @Column(name = "promo_code")
    private String promoCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status = OrderStatus.PENDING;

    @Column(nullable = false)
    private double remaining = 0.0;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();

    public void addItem(OrderItem orderItem) {
        orderItems.add(orderItem);
    }

}
