package com.aliyara.smartshop.dto.response;

import lombok.Builder;

import java.util.UUID;

@Builder
public record OrderItemResponseDTO (
        UUID id,
        int quantity,
        ProductResponseDTO product
) {
}
