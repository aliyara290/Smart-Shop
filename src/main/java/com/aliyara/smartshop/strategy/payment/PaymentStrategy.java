package com.aliyara.smartshop.strategy.payment;

import com.aliyara.smartshop.model.Payment;

public interface PaymentStrategy<T> {
    void process(Payment payment, T paymentMethod);
}
