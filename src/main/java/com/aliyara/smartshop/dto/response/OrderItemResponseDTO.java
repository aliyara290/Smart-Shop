package com.aliyara.smartshop.dto.response;

import lombok.Builder;

@Builder
public record OrderItemResponseDTO (
        String id,
        int quantity,
        ProductResponseDTO product
) {
}
