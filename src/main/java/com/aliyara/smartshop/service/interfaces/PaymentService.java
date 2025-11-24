package com.aliyara.smartshop.service.interfaces;

import com.aliyara.smartshop.dto.request.PaymentRequestDTO;
import com.aliyara.smartshop.dto.request.ProductRequestDTO;
import com.aliyara.smartshop.dto.response.PaymentResponseDTO;
import com.aliyara.smartshop.dto.response.ProductResponseDTO;
import com.aliyara.smartshop.payload.ApiResponse;

import java.util.List;

public interface PaymentService {
    PaymentResponseDTO create(PaymentRequestDTO requestDTO);
    PaymentResponseDTO update(PaymentRequestDTO requestDTO, String id);
    ApiResponse<Void> delete(String id);
    PaymentResponseDTO findById(String id);
    List<PaymentResponseDTO> getAllPayments();
}

