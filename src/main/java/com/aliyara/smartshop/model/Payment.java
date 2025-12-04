package com.aliyara.smartshop.model;

import com.aliyara.smartshop.enums.PaymentStatus;
import com.aliyara.smartshop.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "payments")
@SQLDelete(sql = "update payments set deleted = true, deleted_at = NOW() where id = ?")
@SQLRestriction("deleted = false")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "payment_type")
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "payment_status")
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    @Column(nullable = false)
    private double amount = 0;

    @Column(nullable = false, name = "payment_date", updatable = false)
    private LocalDateTime paymentDate;


    @OneToOne(mappedBy = "payment", cascade = CascadeType.ALL)
    private CashPayment cashPayment;

    @OneToOne(mappedBy = "payment", cascade = CascadeType.ALL)
    private TransferPayment transferPayment;

    @OneToOne(mappedBy = "payment", cascade = CascadeType.ALL)
    private CheckPayment checkPayment;

    @Column(name = "deleted")
    private boolean deleted;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.paymentDate = LocalDateTime.now();
    }



}
