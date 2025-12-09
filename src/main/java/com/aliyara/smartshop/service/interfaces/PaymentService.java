package com.aliyara.smartshop.service.interfaces;

import com.aliyara.smartshop.dto.request.PaymentRequestDTO;
import com.aliyara.smartshop.dto.response.PaymentResponseDTO;
import com.aliyara.smartshop.payload.ApiResponse;

import java.util.List;
import java.util.UUID;

public interface PaymentService {
    PaymentResponseDTO pay(PaymentRequestDTO requestDTO, UUID orderId);
    PaymentResponseDTO update(PaymentRequestDTO requestDTO, UUID id);
    PaymentResponseDTO findById(UUID id);
    List<PaymentResponseDTO> getAllPayments();
    double checkOrderRemainingAmount(UUID orderId);
}

