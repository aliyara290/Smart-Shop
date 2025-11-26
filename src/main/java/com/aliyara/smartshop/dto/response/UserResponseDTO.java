package com.aliyara.smartshop.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserResponseDTO(
        String id,
        String firstName,
        String lastName,
        String email,
        String role,
        boolean isDeleted,
        LocalDateTime deletedAt
) {
}
