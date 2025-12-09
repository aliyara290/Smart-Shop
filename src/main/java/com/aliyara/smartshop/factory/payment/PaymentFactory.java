package com.aliyara.smartshop.factory.payment;

import com.aliyara.smartshop.enums.PaymentMethod;
import com.aliyara.smartshop.strategy.payment.CashPaymentStrategy;
import com.aliyara.smartshop.strategy.payment.CheckPaymentStrategy;
import com.aliyara.smartshop.strategy.payment.PaymentStrategy;
import com.aliyara.smartshop.strategy.payment.TransferPaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PaymentFactory {

    private final CashPaymentStrategy cashPaymentStrategy;
    private final CheckPaymentStrategy checkPaymentStrategy;
    private final TransferPaymentStrategy transferPaymentStrategy;

    public PaymentStrategy createPayment(PaymentMethod paymentMethod) {
        return switch (paymentMethod) {
            case CASH -> cashPaymentStrategy;
            case CHECK -> checkPaymentStrategy;
            case TRANSFER -> transferPaymentStrategy;
        };
    }
}
