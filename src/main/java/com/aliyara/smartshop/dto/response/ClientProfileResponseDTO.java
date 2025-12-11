package com.aliyara.smartshop.dto.response;

import com.aliyara.smartshop.enums.ClientLoyaltyLevel;
import com.aliyara.smartshop.enums.UserRole;

public record ClientProfileResponseDTO(
        String firstName,
        String lastName,
        String email,
        UserRole role,
        ClientLoyaltyLevel loyaltyLevel
) {
}
