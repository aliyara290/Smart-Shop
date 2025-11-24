package com.aliyara.smartshop.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
public class OrderRequestDTO {
    private String promoCode;
    private List<OrderItemRequestDTO> orderItems;
}
