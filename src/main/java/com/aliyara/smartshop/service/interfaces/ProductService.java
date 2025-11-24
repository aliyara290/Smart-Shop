package com.aliyara.smartshop.service.interfaces;

import com.aliyara.smartshop.dto.request.ProductRequestDTO;
import com.aliyara.smartshop.dto.response.ProductResponseDTO;
import com.aliyara.smartshop.payload.ApiResponse;

import java.util.List;

public interface ProductService {
    ProductResponseDTO create(ProductRequestDTO requestDTO);
    ProductResponseDTO update(ProductRequestDTO requestDTO, String id);
    ApiResponse<Void> delete(String id);
    ProductResponseDTO findById(String id);
    List<ProductResponseDTO> getAllProducts();
}
