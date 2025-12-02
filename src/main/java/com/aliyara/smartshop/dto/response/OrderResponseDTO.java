package com.aliyara.smartshop.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record OrderResponseDTO(
        UUID id,
        double subtotal,
        double discount,
        double total,
        double VAT,
        String promoCode,
        String status,
        List<OrderItemResponseDTO> orderItems,
        LocalDateTime createdAt
) {
}