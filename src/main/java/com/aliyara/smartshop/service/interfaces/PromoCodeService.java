package com.aliyara.smartshop.service.interfaces;

import com.aliyara.smartshop.dto.request.PromoCodeRequestDTO;
import com.aliyara.smartshop.dto.response.PromoCodeResponseDTO;
import com.aliyara.smartshop.payload.ApiResponse;

import java.util.List;
import java.util.UUID;

public interface PromoCodeService {
    PromoCodeResponseDTO create(PromoCodeRequestDTO requestDTO);
    PromoCodeResponseDTO update(PromoCodeRequestDTO requestDTO, UUID id);
    ApiResponse<Void> delete(UUID id);
    List<PromoCodeResponseDTO> getAll();
    PromoCodeResponseDTO getById(UUID id);
    boolean checkIfPromoCodeValid(String promoCode);
}