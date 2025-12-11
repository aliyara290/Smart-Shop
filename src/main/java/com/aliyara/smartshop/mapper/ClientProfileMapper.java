package com.aliyara.smartshop.mapper;

import com.aliyara.smartshop.dto.response.ClientProfileResponseDTO;
import com.aliyara.smartshop.dto.response.UserResponseDTO;
import com.aliyara.smartshop.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientProfileMapper {

    @Mapping(source = "client.user.firstName", target = "firstName")
    @Mapping(source = "client.user.lastName", target = "lastName")
    @Mapping(source = "client.user.role", target = "role")
    @Mapping(source = "client.user.email", target = "email")
    ClientProfileResponseDTO toResponse(Client client);
}
