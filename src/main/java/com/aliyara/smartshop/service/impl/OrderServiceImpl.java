package com.aliyara.smartshop.service.impl;

import com.aliyara.smartshop.dto.request.OrderRequestDTO;
import com.aliyara.smartshop.dto.response.OrderResponseDTO;
import com.aliyara.smartshop.mapper.OrderMapper;
import com.aliyara.smartshop.payload.ApiResponse;
import com.aliyara.smartshop.repository.OrderRepository;
import com.aliyara.smartshop.service.interfaces.OrderService;
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
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public OrderResponseDTO create(OrderRequestDTO requestDTO) {
        return null;
    }

    @Override
    public OrderResponseDTO update(OrderRequestDTO requestDTO, UUID id) {
        return null;
    }

    @Override
    public ApiResponse<Void> softDelete(UUID id) {
        return null;
    }

    @Override
    public OrderResponseDTO findById(UUID id) {
        return null;
    }

    @Override
    public List<OrderResponseDTO> getAllOrders() {
        return List.of();
    }
}
