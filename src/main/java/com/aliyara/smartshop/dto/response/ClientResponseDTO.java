package com.aliyara.smartshop.dto.response;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ClientResponseDTO (
        UUID id,
        UserResponseDTO user,
        String loyaltyLevel
) {
}
