package com.aliyara.smartshop.service.interfaces;

import com.aliyara.smartshop.dto.request.OrderRequestDTO;
import com.aliyara.smartshop.dto.response.OrderResponseDTO;
import com.aliyara.smartshop.payload.ApiResponse;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    OrderResponseDTO create(OrderRequestDTO requestDTO);
    OrderResponseDTO update(OrderRequestDTO requestDTO, UUID id);
    ApiResponse<Void> softDelete(UUID id);
    OrderResponseDTO findById(UUID id);
    List<OrderResponseDTO> getAllOrders();
}

