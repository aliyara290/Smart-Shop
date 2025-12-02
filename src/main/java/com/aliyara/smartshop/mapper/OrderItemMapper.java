package com.aliyara.smartshop.mapper;

import com.aliyara.smartshop.dto.response.OrderItemResponseDTO;
import com.aliyara.smartshop.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    @Mapping(source = "product.id", target = "productId")
    OrderItemResponseDTO toResponse(OrderItem orderItem);
//    OrderItem toEntity(OrderItemRequestDTO requestDTO);
}
