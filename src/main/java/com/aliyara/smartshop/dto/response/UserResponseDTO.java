package com.aliyara.smartshop.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record UserResponseDTO(
        UUID id,
        String firstName,
        String lastName,
        String email,
        String role,
        boolean isDeleted,
        LocalDateTime deletedAt,
        LocalDateTime createdAt
) {
}