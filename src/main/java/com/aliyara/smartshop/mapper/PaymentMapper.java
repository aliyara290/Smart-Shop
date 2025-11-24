package com.aliyara.smartshop.mapper;

import com.aliyara.smartshop.dto.request.ProductRequestDTO;
import com.aliyara.smartshop.dto.response.PaymentResponseDTO;
import com.aliyara.smartshop.model.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentResponseDTO toResponse(Payment payment);
    Payment toEntity(ProductRequestDTO requestDTO);
}
