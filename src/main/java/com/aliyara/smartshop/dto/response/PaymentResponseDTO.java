package com.aliyara.smartshop.dto.response;

import com.aliyara.smartshop.enums.PaymentMethod;
import lombok.Builder;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record PaymentResponseDTO (
        UUID id,
        PaymentMethod paymentMethod,
        String paymentStatus,
        double amount,
        LocalDateTime paymentDate
) {
}
