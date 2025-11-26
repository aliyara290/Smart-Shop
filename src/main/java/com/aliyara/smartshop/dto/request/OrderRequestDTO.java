package com.aliyara.smartshop.dto.request;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class OrderRequestDTO {
    private String promoCode;
    private List<OrderItemRequestDTO> orderItems;
}
