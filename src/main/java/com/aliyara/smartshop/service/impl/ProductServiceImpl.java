package com.aliyara.smartshop.service.impl;

import com.aliyara.smartshop.dto.request.ProductRequestDTO;
import com.aliyara.smartshop.dto.response.ProductResponseDTO;
import com.aliyara.smartshop.exception.ResourceNotFoundException;
import com.aliyara.smartshop.mapper.ProductMapper;
import com.aliyara.smartshop.model.Product;
import com.aliyara.smartshop.payload.ApiResponse;
import com.aliyara.smartshop.repository.ProductRepository;
import com.aliyara.smartshop.service.interfaces.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductResponseDTO create(ProductRequestDTO requestDTO) {
        Product product = productMapper.toEntity(requestDTO);
        Product savedProduct = productRepository.save(product);
        return productMapper.toResponse(savedProduct);
    }

    @Override
    public ProductResponseDTO update(ProductRequestDTO requestDTO, String id) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with ID: " + id + " not found!"));
        productMapper.updateProductFromDTO(requestDTO, existingProduct);
        Product savedProduct = productRepository.save(existingProduct);
        return productMapper.toResponse(savedProduct);
    }

    @Override
    public ApiResponse<Void> softDelete(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with ID: " + id + " not found!"));
        if(!product.isDeleted()) {
            product.setDeleted(true);
            product.setDeletedAt(LocalDateTime.now());
        }
        return new ApiResponse<>(true, "Product Deleted successfully!", LocalDateTime.now(), null);
     }

    @Override
    public ProductResponseDTO findById(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with ID " + id + " not exist!"));
        return productMapper.toResponse(product);
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(productMapper::toResponse).toList();
    }
}