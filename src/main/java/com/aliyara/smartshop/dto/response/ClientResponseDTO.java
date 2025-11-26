package com.aliyara.smartshop.dto.response;

import lombok.Builder;

@Builder
public record ClientResponseDTO (
        String id,
        UserResponseDTO user,
        String loyaltyLevel
) {
}
