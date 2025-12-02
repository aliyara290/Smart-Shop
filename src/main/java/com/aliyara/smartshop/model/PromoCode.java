package com.aliyara.smartshop.model;

import com.aliyara.smartshop.enums.DiscountType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "promo_codes")
@SQLDelete(sql = "update promo_code set deleted = true, deleted_at = NOW() where id = ?")
@SQLRestriction("deleted = false")
public class PromoCode {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(name = "discount_value")
    private double discountValue;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "discount_type")
    private DiscountType discountType;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(nullable = false, name = "max_usage")
    private int maxUsage;

    @Column(nullable = false, name = "active")
    private boolean active = true;

    @Column(name = "used_count")
    private int usedCount = 0;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "deleted")
    private boolean deleted = false;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @PrePersist
    public void updateCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }

}
