package com.aliyara.smartshop.service.interfaces;


import com.aliyara.smartshop.dto.request.UserRequestDTO;
import com.aliyara.smartshop.dto.response.UserResponseDTO;
import com.aliyara.smartshop.model.User;
import com.aliyara.smartshop.payload.ApiResponse;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserResponseDTO create(UserRequestDTO requestDTO);
    UserResponseDTO update(UserRequestDTO requestDTO, UUID id);
    ApiResponse<Void> delete(UUID id);
    UserResponseDTO findById(UUID id);
    List<UserResponseDTO> getAllUsers();
}
