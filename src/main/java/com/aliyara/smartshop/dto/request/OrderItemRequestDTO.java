package com.aliyara.smartshop.dto.request;

import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class OrderItemRequestDTO {
    private int quantity;
    private UUID productId;
}
