package com.aliyara.smartshop.dto.request;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PaymentRequestDTO {
    private String paymentType;
    private String paymentStatus;
    private double amount;
    private LocalDateTime paymentDate;
}
