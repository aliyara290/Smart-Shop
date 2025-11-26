package com.aliyara.smartshop.dto.response;

import lombok.Builder;

import java.util.List;
@Builder
public record OrderResponseDTO(
        String id,
        double subtotal,
        double total,
        double VAT,
        String promoCode,
        String status,
        List<OrderItemResponseDTO> orderItems
) {
}