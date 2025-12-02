package com.aliyara.smartshop.model;

import com.aliyara.smartshop.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "orders")
@SQLDelete(sql = "update orders set deleted = true, deleted_at = NOW() where id = ?")
@SQLRestriction("deleted = false")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

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

    @ManyToOne
    @JoinColumn(name = "promo_code_id")
    private PromoCode promoCode;

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

    @Column(name = "deleted")
    private boolean deleted;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public void addItem(OrderItem orderItem) {
        orderItems.add(orderItem);
    }


    @PrePersist
    public void updateCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }

}
