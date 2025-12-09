package com.aliyara.smartshop.strategy.payment;

import com.aliyara.smartshop.enums.PaymentStatus;
import com.aliyara.smartshop.exception.MissingFieldException;
import com.aliyara.smartshop.model.Payment;
import com.aliyara.smartshop.model.TransferPayment;
import com.aliyara.smartshop.repository.TransferPaymentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class TransferPaymentStrategy implements PaymentStrategy<TransferPayment> {

    private final TransferPaymentRepository transferPaymentRepository;

    @Override
    public void process(Payment payment, TransferPayment paymentMethod) {
        if(paymentMethod.getBankName() == null) {
            throw new MissingFieldException("You must enter Bank name!");
        }

        if(paymentMethod.getReferenceNumber() == null) {
            throw new MissingFieldException("You must enter reference number!");
        }

        if(paymentMethod.getSenderName() == null) {
            throw new MissingFieldException("You must enter sender name!");
        }
        payment.setPaymentStatus(PaymentStatus.CASHED);
        paymentMethod.setPayment(payment);

        transferPaymentRepository.save(paymentMethod);
    }
}
