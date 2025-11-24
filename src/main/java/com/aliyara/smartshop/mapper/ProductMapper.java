package com.aliyara.smartshop.mapper;

import com.aliyara.smartshop.dto.request.ProductRequestDTO;
import com.aliyara.smartshop.dto.response.ProductResponseDTO;
import com.aliyara.smartshop.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponseDTO toResponse(Product product);
    Product toEntity(ProductRequestDTO requestDTO);
}
