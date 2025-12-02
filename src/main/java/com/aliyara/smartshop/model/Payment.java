package com.aliyara.smartshop.model;

import com.aliyara.smartshop.enums.PaymentStatus;
import com.aliyara.smartshop.enums.PaymentType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    @Column(name = "deleted")
    private boolean deleted;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void updateCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }

}
