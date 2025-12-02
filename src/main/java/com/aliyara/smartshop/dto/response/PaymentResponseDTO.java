package com.aliyara.smartshop.dto.response;

import lombok.Builder;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record PaymentResponseDTO (
        UUID id,
        String paymentType,
        String paymentStatus,
        double amount,
        LocalDateTime paymentDate,
        LocalDateTime collectionDate,
        LocalDateTime createdAt
) {
}
