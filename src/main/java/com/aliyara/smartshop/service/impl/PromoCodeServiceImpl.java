package com.aliyara.smartshop.service.impl;

import com.aliyara.smartshop.dto.request.PromoCodeRequestDTO;
import com.aliyara.smartshop.dto.response.PromoCodeResponseDTO;
import com.aliyara.smartshop.exception.DuplicateResourceException;
import com.aliyara.smartshop.exception.ExpiredPromoCodeException;
import com.aliyara.smartshop.exception.ResourceNotFoundException;
import com.aliyara.smartshop.mapper.PromoCodeMapper;
import com.aliyara.smartshop.model.PromoCode;
import com.aliyara.smartshop.payload.ApiResponse;
import com.aliyara.smartshop.repository.PromoCodeRepository;
import com.aliyara.smartshop.service.interfaces.PromoCodeService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional
@Slf4j
@Service
public class PromoCodeServiceImpl implements PromoCodeService {

    private final PromoCodeRepository promoCodeRepository;
    private final PromoCodeMapper promoCodeMapper;

    @Override
    public PromoCodeResponseDTO create(PromoCodeRequestDTO requestDTO) {
        PromoCode promoCode = promoCodeMapper.toEntity(requestDTO);
        promoCode.setDiscountValue(requestDTO.getDiscountValue());
        PromoCode savedPromoCode = promoCodeRepository.save(promoCode);
        return promoCodeMapper.toResponse(savedPromoCode);
    }

    @Override
    public PromoCodeResponseDTO update(PromoCodeRequestDTO requestDTO, UUID id) {
        PromoCode existingPromoCode = promoCodeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Promo code not exist!"));
        promoCodeMapper.updatePromoCodeFromDTO(requestDTO, existingPromoCode);

        if (requestDTO.getCode() != null) {
            if (promoCodeRepository.existsPromoCodeByCode(requestDTO.getCode())) {
                throw new DuplicateResourceException("The code is already exist! try another one!");
            }
            existingPromoCode.setCode(requestDTO.getCode());
        }
        PromoCode savedPromoCode = promoCodeRepository.save(existingPromoCode);
        return promoCodeMapper.toResponse(savedPromoCode);
    }

    @Override
    public ApiResponse<Void> delete(UUID id) {
        PromoCode findPromoCode = promoCodeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Promo code not exist!"));
        promoCodeRepository.delete(findPromoCode);
        return new ApiResponse<>(true, "Promo code deleted successfully", LocalDateTime.now(), null);
    }

    @Override
    public List<PromoCodeResponseDTO> getAll() {
        List<PromoCode> promoCodes = promoCodeRepository.findAll();
        return promoCodes.stream().map(promoCodeMapper::toResponse).toList();
    }

    @Override
    public PromoCodeResponseDTO getById(UUID id) {
        PromoCode promoCode = promoCodeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Promo code not exist!"));
        return promoCodeMapper.toResponse(promoCode);
    }

    @Transactional(noRollbackFor = ExpiredPromoCodeException.class)
    @Override
    public boolean checkIfPromoCodeValid(String code) {
        PromoCode promoCode = promoCodeRepository.findByCode(code)
                .orElseThrow(() -> new ResourceNotFoundException("Promo code not exist!"));

        boolean isExpired =
                !promoCode.isActive() ||
                        promoCode.getUsedCount() >= promoCode.getMaxUsage() ||
                        promoCode.getEndDate().isBefore(LocalDate.now()) ||
                        promoCode.isDeleted();

        if (isExpired) {
            promoCode.setActive(false);
            promoCodeRepository.save(promoCode);
            throw new ExpiredPromoCodeException();
        }

        return true;
    }

}