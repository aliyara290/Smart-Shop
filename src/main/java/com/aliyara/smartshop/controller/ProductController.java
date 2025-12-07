package com.aliyara.smartshop.controller;

import com.aliyara.smartshop.dto.request.ProductRequestDTO;
import com.aliyara.smartshop.dto.response.ProductResponseDTO;
import com.aliyara.smartshop.payload.ApiResponse;
import com.aliyara.smartshop.service.interfaces.ProductService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/products")
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponseDTO>> createProduct(@Valid @RequestBody ProductRequestDTO requestDTO) {
        ProductResponseDTO savedProduct = productService.create(requestDTO);
        ApiResponse<ProductResponseDTO> response = new ApiResponse<>(true, "Product created successfully", LocalDateTime.now(), savedProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> updateProduct(@Valid @RequestBody ProductRequestDTO requestDTO, @PathVariable UUID id) {
        ProductResponseDTO updatedProduct = productService.update(requestDTO, id);
        ApiResponse<ProductResponseDTO> response = new ApiResponse<>(true, "Product updated successfully", LocalDateTime.now(), updatedProduct);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable UUID id) {
        ApiResponse<Void> deletedProduct = productService.softDelete(id);
        return ResponseEntity.ok().body(deletedProduct);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDTO>> getProduct(@PathVariable UUID id) {
        ProductResponseDTO product = productService.findById(id);
        ApiResponse<ProductResponseDTO> response = new ApiResponse<>(true, "Product fetched successfully!", LocalDateTime.now(), product);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponseDTO>>> getAllProducts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "6") int size) {
        List<ProductResponseDTO> products = productService.getAllProducts(page, size);
        ApiResponse<List<ProductResponseDTO>> response = new ApiResponse<>(true, "Products fetched successfully!", LocalDateTime.now(), products);
        return ResponseEntity.ok().body(response);
    }
}
