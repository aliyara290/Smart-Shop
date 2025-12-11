package com.aliyara.smartshop.service.interfaces;

import com.aliyara.smartshop.dto.request.ProductRequestDTO;
import com.aliyara.smartshop.dto.response.ProductResponseDTO;
import com.aliyara.smartshop.payload.ApiResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductResponseDTO create(ProductRequestDTO requestDTO);
    ProductResponseDTO update(ProductRequestDTO requestDTO, UUID id);
    ApiResponse<Void> softDelete(UUID id);
    ProductResponseDTO findById(UUID id);
    Page<ProductResponseDTO> getAllProducts(int page, int size);
}
