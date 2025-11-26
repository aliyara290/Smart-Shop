package com.aliyara.smartshop.model;

import com.aliyara.smartshop.model.enums.PaymentStatus;
import com.aliyara.smartshop.model.enums.PaymentType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "payment_type")
    private PaymentType paymentType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "payment_status")
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    @Column(nullable = false)
    private double amount = 0;

    @Column(nullable = false, name = "payment_date")
    private LocalDateTime paymentDate = LocalDateTime.now();

    @Column(nullable = false, name = "collection_date")
    private LocalDateTime collectionDate;
}
