package com.aliyara.smartshop.payload;

import java.time.LocalDateTime;

public record ApiResponse<T>(
        boolean state,
        String message,
        LocalDateTime timestamp,
        T data
) {
}