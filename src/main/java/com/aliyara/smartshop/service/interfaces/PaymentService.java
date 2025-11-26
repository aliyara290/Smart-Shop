package com.aliyara.smartshop.service.interfaces;

import com.aliyara.smartshop.dto.request.PaymentRequestDTO;
import com.aliyara.smartshop.dto.request.ProductRequestDTO;
import com.aliyara.smartshop.dto.response.PaymentResponseDTO;
import com.aliyara.smartshop.dto.response.ProductResponseDTO;
import com.aliyara.smartshop.payload.ApiResponse;

import java.util.List;
import java.util.UUID;

public interface PaymentService {
    PaymentResponseDTO create(PaymentRequestDTO requestDTO);
    PaymentResponseDTO update(PaymentRequestDTO requestDTO, UUID id);
    ApiResponse<Void> delete(UUID id);
    PaymentResponseDTO findById(UUID id);
    List<PaymentResponseDTO> getAllPayments();
}

