package com.aliyara.smartshop.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ProductRequestDTO {
    private String name;
    private double unitPrice;
    private int stock;
}