package com.aliyara.smartshop.strategy.payment;

import com.aliyara.smartshop.enums.PaymentStatus;
import com.aliyara.smartshop.exception.MissingFieldException;
import com.aliyara.smartshop.model.CashPayment;
import com.aliyara.smartshop.model.Payment;
import com.aliyara.smartshop.repository.CashPaymentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class CashPaymentStrategy implements PaymentStrategy<CashPayment> {

    private final CashPaymentRepository cashPaymentRepository;

    @Override
    public void process(Payment payment, CashPayment paymentMethod) {
        if(paymentMethod.getReceiptNumber() == null || paymentMethod.getReceiptNumber().isEmpty()) {
            throw new MissingFieldException("You must enter receipt number!");
        }
        payment.setPaymentStatus(PaymentStatus.CASHED);
        paymentMethod.setPayment(payment);
        cashPaymentRepository.save(paymentMethod);
    }
}
