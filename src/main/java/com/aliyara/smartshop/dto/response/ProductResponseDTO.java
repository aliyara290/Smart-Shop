package com.aliyara.smartshop.dto.response;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ProductResponseDTO(
        UUID id,
        String name,
        double unitPrice,
        int stock
) {
}
