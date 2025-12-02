package com.aliyara.smartshop.dto.response;

import com.aliyara.smartshop.enums.DiscountType;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record PromoCodeResponseDTO(
        String code,
        DiscountType discountType,
        double discountValue,
        LocalDate endDate,
        int maxUsage,
        int usedCount,
        boolean active,
        LocalDateTime createdAt
) {
}
