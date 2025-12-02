package com.aliyara.smartshop.dto.request;

import com.aliyara.smartshop.enums.DiscountType;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PromoCodeRequestDTO {
    private String code;
    private DiscountType discountType;
    private double discountValue;
    private LocalDate endDate;
    private int maxUsage;
}