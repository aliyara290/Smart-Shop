package com.aliyara.smartshop.dto.response;

import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record OrderResponseDTO(
        UUID id,
        double subtotal,
        double total,
        double VAT,
        String promoCode,
        String status,
        List<OrderItemResponseDTO> orderItems
) {
}