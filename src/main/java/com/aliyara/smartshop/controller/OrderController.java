package com.aliyara.smartshop.controller;


import com.aliyara.smartshop.dto.request.OrderRequestDTO;
import com.aliyara.smartshop.dto.response.OrderResponseDTO;
import com.aliyara.smartshop.payload.ApiResponse;
import com.aliyara.smartshop.service.interfaces.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponseDTO>> createOrder(@Valid @RequestBody OrderRequestDTO requestDTO) {
        OrderResponseDTO order = orderService.create(requestDTO);
        ApiResponse<OrderResponseDTO> response = new ApiResponse<>(true, "Order created successfully!", LocalDateTime.now(), order);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
