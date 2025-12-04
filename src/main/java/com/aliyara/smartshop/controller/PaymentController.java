package com.aliyara.smartshop.controller;

import com.aliyara.smartshop.dto.request.PaymentRequestDTO;
import com.aliyara.smartshop.dto.response.PaymentResponseDTO;
import com.aliyara.smartshop.payload.ApiResponse;
import com.aliyara.smartshop.service.interfaces.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/order/{orderId}")
    public ResponseEntity<ApiResponse<PaymentResponseDTO>> pay(@Valid @RequestBody PaymentRequestDTO requestDTO, @PathVariable UUID orderId) {
        PaymentResponseDTO payOrder = paymentService.pay(requestDTO, orderId);
        ApiResponse<PaymentResponseDTO> response = new ApiResponse<>(true, "Product paid successfully!", LocalDateTime.now(), payOrder);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PaymentResponseDTO>> update(@Valid @RequestBody PaymentRequestDTO requestDTO, @PathVariable UUID id) {
        PaymentResponseDTO updatedPayment = paymentService.update(requestDTO, id);
        ApiResponse<PaymentResponseDTO> response = new ApiResponse<>(true, "Payment updated successfully!", LocalDateTime.now(), updatedPayment);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PaymentResponseDTO>> findById(@PathVariable UUID id) {
        PaymentResponseDTO payment = paymentService.findById(id);
        ApiResponse<PaymentResponseDTO> response = new ApiResponse<>(true, "Payment fetched successfully!", LocalDateTime.now(), payment);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PaymentResponseDTO>>> getAllPayments() {
        List<PaymentResponseDTO> payments = paymentService.getAllPayments();
        ApiResponse<List<PaymentResponseDTO>> response = new ApiResponse<>(true, "Payments fetched successfully!", LocalDateTime.now(), payments);
        return ResponseEntity.ok(response);
    }
}
