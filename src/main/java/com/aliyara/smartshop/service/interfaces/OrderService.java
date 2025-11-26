package com.aliyara.smartshop.service.interfaces;

import com.aliyara.smartshop.dto.request.OrderRequestDTO;
import com.aliyara.smartshop.dto.response.OrderResponseDTO;
import com.aliyara.smartshop.payload.ApiResponse;

import java.util.List;

public interface OrderService {
    OrderResponseDTO create(OrderRequestDTO requestDTO);
    OrderResponseDTO update(OrderRequestDTO requestDTO, String id);
    ApiResponse<Void> softDelete(String id);
    OrderResponseDTO findById(String id);
    List<OrderResponseDTO> getAllOrders();
}

