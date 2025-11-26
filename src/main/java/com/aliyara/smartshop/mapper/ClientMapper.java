package com.aliyara.smartshop.mapper;

import com.aliyara.smartshop.dto.request.ClientRequestDTO;
import com.aliyara.smartshop.dto.response.ClientResponseDTO;
import com.aliyara.smartshop.dto.response.UserResponseDTO;
import com.aliyara.smartshop.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {UserResponseDTO.class})
public interface ClientMapper {
    ClientResponseDTO toResponse(Client client);
    Client toEntity(ClientRequestDTO requestDTO);
    void updateClientFromDTO(ClientRequestDTO requestDTO, @MappingTarget Client client);
}
