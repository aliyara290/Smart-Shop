package com.aliyara.smartshop.mapper;

import com.aliyara.smartshop.dto.request.PromoCodeRequestDTO;
import com.aliyara.smartshop.dto.response.PromoCodeResponseDTO;
import com.aliyara.smartshop.model.PromoCode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PromoCodeMapper {
    PromoCodeResponseDTO toResponse(PromoCode promoCode);

    @Mappings({
        @Mapping(target = "id", ignore = true),
        @Mapping(target = "usedCount", ignore = true),
        @Mapping(target = "createdAt", ignore = true),
        @Mapping(target = "deleted", ignore = true),
        @Mapping(target = "deletedAt", ignore = true),
        @Mapping(target = "active", ignore = true)
    })
    PromoCode toEntity(PromoCodeRequestDTO requestDTO);

    void updatePromoCodeFromDTO(PromoCodeRequestDTO requestDTO, @MappingTarget PromoCode promoCode);
}
