package com.aliyara.smartshop.dto.response;

import lombok.Builder;

@Builder
public record OrderItemResponseDTO (
        int quantity,
        ProductResponseDTO product
) {
}
