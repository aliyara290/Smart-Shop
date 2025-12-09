package com.aliyara.smartshop.dto.request;

import com.aliyara.smartshop.enums.PaymentMethod;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PaymentRequestDTO {
    private PaymentMethod paymentMethod;
    private double amount;
    private CashPaymentRequestDTO cashPayment;
    private CheckPaymentRequestDTO checkPayment;
    private TransferPaymentRequestDTO transferPayment;
}