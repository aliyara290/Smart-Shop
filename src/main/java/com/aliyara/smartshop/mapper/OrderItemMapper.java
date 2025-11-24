package com.aliyara.smartshop.mapper;

import com.aliyara.smartshop.dto.request.OrderItemRequestDTO;
import com.aliyara.smartshop.dto.response.OrderItemResponseDTO;
import com.aliyara.smartshop.model.OrderItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    OrderItemResponseDTO toResponse(OrderItem orderItem);
    OrderItem toEntity(OrderItemRequestDTO requestDTO);
}
