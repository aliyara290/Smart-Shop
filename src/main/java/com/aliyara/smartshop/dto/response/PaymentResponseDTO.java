package com.aliyara.smartshop.dto.response;

import lombok.Builder;
import java.time.LocalDateTime;

@Builder
public record PaymentResponseDTO (
        String id,
        String paymentType,
        String paymentStatus,
        double amount,
        LocalDateTime paymentDate,
        LocalDateTime collectionDate
) {
}
