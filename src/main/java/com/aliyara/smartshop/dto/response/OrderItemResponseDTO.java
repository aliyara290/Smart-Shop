package com.aliyara.smartshop.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record OrderItemResponseDTO (
        UUID id,
        int quantity,
        String productId,
        LocalDateTime createdAt
) {
}
