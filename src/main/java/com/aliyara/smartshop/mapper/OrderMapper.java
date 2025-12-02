package com.aliyara.smartshop.mapper;

import com.aliyara.smartshop.dto.request.OrderRequestDTO;
import com.aliyara.smartshop.dto.response.OrderResponseDTO;
import com.aliyara.smartshop.model.Order;
import com.aliyara.smartshop.model.PromoCode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface OrderMapper {

    @Mapping(target = "promoCode", source = "promoCode", qualifiedByName = "promoCodeToString")
    OrderResponseDTO toResponse(Order order);

    @Named("promoCodeToString")
    default String promoCodeToString(PromoCode promoCode) {
        return promoCode != null ? promoCode.getCode() : null;
    }

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "orderDate", ignore = true),
            @Mapping(target = "subtotal", ignore = true),
            @Mapping(target = "discount", ignore = true),
            @Mapping(target = "VAT", ignore = true),
            @Mapping(target = "total", ignore = true),
            @Mapping(target = "status", ignore = true),
            @Mapping(target = "remaining", ignore = true),
            @Mapping(target = "client", ignore = true),
            @Mapping(target = "deleted", ignore = true),
            @Mapping(target = "deletedAt", ignore = true),
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "promoCode", ignore = true)
    })
    Order toEntity(OrderRequestDTO requestDTO);
}
