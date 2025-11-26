package com.aliyara.smartshop.model;

import com.aliyara.smartshop.model.enums.DiscountType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "promo_codes")
public class PromoCode {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "discount_type")
    private DiscountType discountType;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(nullable = false, name = "max_usage")
    private int maxUsage;

    private boolean active = true;

    @Column(name = "used_count")
    private int usedCount = 0;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
