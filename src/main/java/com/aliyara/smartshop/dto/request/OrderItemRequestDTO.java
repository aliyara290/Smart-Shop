package com.aliyara.smartshop.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class OrderItemRequestDTO {
    private int quantity;
    private String productId;
}
