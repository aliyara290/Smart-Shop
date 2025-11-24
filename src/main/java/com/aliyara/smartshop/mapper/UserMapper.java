package com.aliyara.smartshop.mapper;

import com.aliyara.smartshop.dto.request.UserRequestDTO;
import com.aliyara.smartshop.dto.response.UserResponseDTO;
import com.aliyara.smartshop.model.User;
import org.mapstruct.Mapper;

@Mapper (componentModel = "spring")
public interface UserMapper {
    UserResponseDTO toResponse(User user);
    User toEntity(UserRequestDTO requestDTO);
}
