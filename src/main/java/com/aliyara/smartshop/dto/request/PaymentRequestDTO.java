package com.aliyara.smartshop.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class PaymentRequestDTO {
    private String paymentType;
    private String paymentStatus;
    private double amount;
    private LocalDateTime paymentDate;
}
