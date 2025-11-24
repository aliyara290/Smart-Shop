package com.aliyara.smartshop.mapper;

import com.aliyara.smartshop.dto.request.ClientRequestDTO;
import com.aliyara.smartshop.dto.response.ClientResponseDTO;
import com.aliyara.smartshop.model.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientResponseDTO toResponse(Client client);
    Client toEntity(ClientRequestDTO requestDTO);
}
