package com.aliyara.smartshop.dto.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class OrderItemRequestDTO {
    private int quantity;
    private String productId;
}
