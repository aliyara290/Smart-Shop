package com.aliyara.smartshop.payload;

import java.time.LocalDateTime;

public record ApiResponse<T>(
        Integer status,
        String message,
        LocalDateTime timestamp,
        T data
) {
}
