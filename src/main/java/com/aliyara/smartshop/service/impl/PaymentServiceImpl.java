package com.aliyara.smartshop.service.impl;

import com.aliyara.smartshop.dto.request.PaymentRequestDTO;
import com.aliyara.smartshop.dto.response.PaymentResponseDTO;
import com.aliyara.smartshop.enums.OrderStatus;
import com.aliyara.smartshop.enums.PaymentStatus;
import com.aliyara.smartshop.exception.PaymentExceedsRemainingException;
import com.aliyara.smartshop.exception.ResourceNotFoundException;
import com.aliyara.smartshop.factory.payment.PaymentFactory;
import com.aliyara.smartshop.mapper.PaymentMapper;
import com.aliyara.smartshop.model.*;
import com.aliyara.smartshop.repository.OrderRepository;
import com.aliyara.smartshop.repository.PaymentRepository;
import com.aliyara.smartshop.service.interfaces.OrderService;
import com.aliyara.smartshop.service.interfaces.PaymentService;
import com.aliyara.smartshop.strategy.payment.PaymentStrategy;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final OrderRepository orderRepository;
    private final PaymentFactory paymentFactory;
    private final OrderService orderService;

    @Override
    public PaymentResponseDTO pay(PaymentRequestDTO requestDTO, UUID orderId) {

        if(checkOrderRemainingAmount(orderId) < requestDTO.getAmount()) {
            throw new PaymentExceedsRemainingException("Payment amount exceeds remaining order balance. Remaining: " + checkOrderRemainingAmount(orderId));
        }

        Payment payment = new Payment();
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setPaymentMethod(requestDTO.getPaymentMethod());
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order with ID: " + orderId + " not found!"));
        payment.setOrder(order);
        payment.setAmount(requestDTO.getAmount());

        log.debug("cash: {}", requestDTO.getCashPayment());

        Object paymentMethod = null;
        switch (requestDTO.getPaymentMethod()) {
            case CASH -> {
                CashPayment cashPayment = new CashPayment();
                cashPayment.setReceiptNumber(requestDTO.getCashPayment().getReceiptNumber());
                payment.setCashPayment(cashPayment);
                paymentMethod = cashPayment;
            }

            case CHECK -> {
                CheckPayment checkPayment = new CheckPayment();
                checkPayment.setCheckNumber(requestDTO.getCheckPayment().getCheckNumber());
                checkPayment.setIssuerName(requestDTO.getCheckPayment().getIssuerName());
                checkPayment.setDueDate(requestDTO.getCheckPayment().getDueDate());
                payment.setCheckPayment(checkPayment);
                paymentMethod = checkPayment;
            }

            case TRANSFER -> {
                TransferPayment transferPayment = new TransferPayment();
                transferPayment.setBankName(requestDTO.getTransferPayment().getBankName());
                transferPayment.setReferenceNumber(requestDTO.getTransferPayment().getReferenceNumber());
                transferPayment.setSenderName(requestDTO.getTransferPayment().getSenderName());
                paymentMethod = transferPayment;
            }
        }

        PaymentStrategy paymentStrategy = paymentFactory.createPayment(requestDTO.getPaymentMethod());
        paymentStrategy.process(payment, paymentMethod);

        double paidOrder = order.getRemaining() - requestDTO.getAmount();
        order.setRemaining(paidOrder);

        if(order.getStatus() == OrderStatus.PENDING) {
            orderService.decreaseProductsStock(order);
        }

        if(order.getRemaining() == 0.0) {
            order.setStatus(OrderStatus.CONFIRMED);
        } else {
            order.setStatus(OrderStatus.PARTIAL_PAID);
        }

        order.addPayment(payment);
        orderRepository.save(order);

        return paymentMapper.toResponse(payment);
    }

    @Override
    public PaymentResponseDTO update(PaymentRequestDTO requestDTO, UUID id) {

        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment with ID: " + id + " not found!"));

        Order order = payment.getOrder();

        order.setRemaining(order.getRemaining() + payment.getAmount());

        if (order.getRemaining() < requestDTO.getAmount()) {
            throw new PaymentExceedsRemainingException(
                    "Updated payment amount exceeds remaining. Remaining: " + order.getRemaining()
            );
        }

        payment.setAmount(requestDTO.getAmount());
        payment.setPaymentMethod(requestDTO.getPaymentMethod());
        payment.setPaymentStatus(PaymentStatus.PENDING);

        payment.setCashPayment(null);
        payment.setCheckPayment(null);
        payment.setTransferPayment(null);

        Object paymentMethod = null;

        switch (requestDTO.getPaymentMethod()) {
            case CASH -> {
                CashPayment cashPayment = new CashPayment();
                cashPayment.setReceiptNumber(requestDTO.getCashPayment().getReceiptNumber());
                payment.setCashPayment(cashPayment);
                paymentMethod = cashPayment;
            }
            case CHECK -> {
                CheckPayment checkPayment = new CheckPayment();
                checkPayment.setCheckNumber(requestDTO.getCheckPayment().getCheckNumber());
                checkPayment.setIssuerName(requestDTO.getCheckPayment().getIssuerName());
                checkPayment.setDueDate(requestDTO.getCheckPayment().getDueDate());
                payment.setCheckPayment(checkPayment);
                paymentMethod = checkPayment;
            }
            case TRANSFER -> {
                TransferPayment transferPayment = new TransferPayment();
                transferPayment.setBankName(requestDTO.getTransferPayment().getBankName());
                transferPayment.setReferenceNumber(requestDTO.getTransferPayment().getReferenceNumber());
                transferPayment.setSenderName(requestDTO.getTransferPayment().getSenderName());
                payment.setTransferPayment(transferPayment);
                paymentMethod = transferPayment;
            }
        }

        PaymentStrategy paymentStrategy =
                paymentFactory.createPayment(requestDTO.getPaymentMethod());

        paymentStrategy.process(payment, paymentMethod);

        order.setRemaining(order.getRemaining() - requestDTO.getAmount());

        if (order.getRemaining() == 0.0) {
            order.setStatus(OrderStatus.CONFIRMED);
        } else {
            order.setStatus(OrderStatus.PARTIAL_PAID);
        }

        paymentRepository.save(payment);

        return paymentMapper.toResponse(payment);
    }

    @Override
    public PaymentResponseDTO findById(UUID id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment with ID: " + id + " not found!"));

        return paymentMapper.toResponse(payment);
    }

    @Override
    public List<PaymentResponseDTO> getAllPayments() {
        return paymentRepository.findAll()
                .stream()
                .map(paymentMapper::toResponse)
                .toList();
    }

    @Override
    public double checkOrderRemainingAmount(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order with ID: " + orderId + " not found!"));
        return order.getRemaining();
    }
}
