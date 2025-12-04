package com.aliyara.smartshop.controller;

import com.aliyara.smartshop.dto.request.OrderRequestDTO;
import com.aliyara.smartshop.dto.response.OrderResponseDTO;
import com.aliyara.smartshop.payload.ApiResponse;
import com.aliyara.smartshop.service.interfaces.OrderService;
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
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponseDTO>> create(@Valid @RequestBody OrderRequestDTO requestDTO) {
        OrderResponseDTO order = orderService.create(requestDTO);
        ApiResponse<OrderResponseDTO> response = new ApiResponse<>(true, "Order created successfully!", LocalDateTime.now(), order);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderResponseDTO>> update(@Valid @RequestBody OrderRequestDTO requestDTO, @PathVariable UUID id) {
        OrderResponseDTO updatedOrder = orderService.update(requestDTO, id);
        ApiResponse<OrderResponseDTO> response = new ApiResponse<>(true, "Order updated successfully!", LocalDateTime.now(), updatedOrder);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> softDelete(@PathVariable UUID id) {
        ApiResponse<Void> response = orderService.softDelete(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderResponseDTO>> findById(@PathVariable UUID id) {
        OrderResponseDTO order = orderService.findById(id);
        ApiResponse<OrderResponseDTO> response = new ApiResponse<>(true, "Order fetched successfully!", LocalDateTime.now(), order);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderResponseDTO>>> getAllOrders() {
        List<OrderResponseDTO> orders = orderService.getAllOrders();
        ApiResponse<List<OrderResponseDTO>> response = new ApiResponse<>(true, "Orders fetched successfully!", LocalDateTime.now(), orders);
        return ResponseEntity.ok(response);
    }
}
