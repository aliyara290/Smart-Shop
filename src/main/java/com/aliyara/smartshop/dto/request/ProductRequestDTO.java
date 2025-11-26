package com.aliyara.smartshop.dto.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ProductRequestDTO {
    private String name;
    private double unitPrice;
    private int stock;
}