package com.aliyara.smartshop.dto.response;

import lombok.Builder;

@Builder
public record ProductResponseDTO(
        String name,
        double unitPrice,
        int stock
) {
}
