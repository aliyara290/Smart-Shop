package com.aliyara.smartshop.strategy.payment;

import com.aliyara.smartshop.enums.PaymentStatus;
import com.aliyara.smartshop.exception.MissingFieldException;
import com.aliyara.smartshop.model.CheckPayment;
import com.aliyara.smartshop.model.Payment;
import com.aliyara.smartshop.repository.CheckPaymentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class CheckPaymentStrategy implements PaymentStrategy<CheckPayment> {

    private final CheckPaymentRepository checkPaymentRepository;

    @Override
    public void process(Payment payment, CheckPayment paymentMethod) {
        if(paymentMethod.getCheckNumber() == null) {
            throw new MissingFieldException("You must enter check number!");
        }

        if(paymentMethod.getIssuerName() == null) {
            throw new MissingFieldException("You must enter issuer name!");
        }

        if(paymentMethod.getDueDate() == null) {
            throw new MissingFieldException("You must enter due date!");
        }
        payment.setPaymentStatus(PaymentStatus.CASHED);
        paymentMethod.setPayment(payment);

        checkPaymentRepository.save(paymentMethod);
    }
}
