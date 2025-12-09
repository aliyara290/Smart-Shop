package com.aliyara.smartshop.dto.request;

import lombok.*;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class OrderRequestDTO {
    private String promoCode;
    private UUID clientId;
    private List<OrderItemRequestDTO> orderItems;
}
