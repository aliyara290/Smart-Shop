package com.aliyara.smartshop.dto.response;

import lombok.Builder;

@Builder
public record ClientResponseDTO (
        String firstName,
        String lastName,
        String email,
        String password,
        String role,
        boolean isActive,
        String loyaltyLevel
) {
}
