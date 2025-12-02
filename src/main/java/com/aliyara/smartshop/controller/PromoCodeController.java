package com.aliyara.smartshop.controller;

import com.aliyara.smartshop.dto.request.PromoCodeRequestDTO;
import com.aliyara.smartshop.dto.response.PromoCodeResponseDTO;
import com.aliyara.smartshop.payload.ApiResponse;
import com.aliyara.smartshop.service.interfaces.PromoCodeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/promo-codes")
public class PromoCodeController {
    
    private final PromoCodeService promoCodeService;

    @PostMapping
    public ResponseEntity<ApiResponse<PromoCodeResponseDTO>> createPromoCode(@Valid @RequestBody PromoCodeRequestDTO requestDTO) {
        PromoCodeResponseDTO savedPromoCode = promoCodeService.create(requestDTO);
        ApiResponse<PromoCodeResponseDTO> response = new ApiResponse<>(true, "PromoCode created successfully", LocalDateTime.now(), savedPromoCode);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PromoCodeResponseDTO>> updatePromoCode(@Valid @RequestBody PromoCodeRequestDTO requestDTO, @PathVariable UUID id) {
        PromoCodeResponseDTO updatedPromoCode = promoCodeService.update(requestDTO, id);
        ApiResponse<PromoCodeResponseDTO> response = new ApiResponse<>(true, "PromoCode updated successfully", LocalDateTime.now(), updatedPromoCode);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePromoCode(@PathVariable UUID id) {
        ApiResponse<Void> deletedPromoCode = promoCodeService.delete(id);
        return ResponseEntity.ok().body(deletedPromoCode);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PromoCodeResponseDTO>> getPromoCode(@PathVariable UUID id) {
        PromoCodeResponseDTO product = promoCodeService.getById(id);
        ApiResponse<PromoCodeResponseDTO> response = new ApiResponse<>(true, "PromoCode fetched successfully!", LocalDateTime.now(), product);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PromoCodeResponseDTO>>> getAllPromoCodes() {
        List<PromoCodeResponseDTO> products = promoCodeService.getAll();
        ApiResponse<List<PromoCodeResponseDTO>> response = new ApiResponse<>(true, "PromoCodes fetched successfully!", LocalDateTime.now(), products);
        return ResponseEntity.ok().body(response);
    }
}
