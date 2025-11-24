package com.aliyara.smartshop.service.interfaces;


import com.aliyara.smartshop.dto.request.UserRequestDTO;
import com.aliyara.smartshop.dto.response.UserResponseDTO;
import com.aliyara.smartshop.model.User;
import com.aliyara.smartshop.payload.ApiResponse;

import java.util.List;

public interface UserService {
    UserResponseDTO create(UserRequestDTO requestDTO);
    UserResponseDTO update(UserRequestDTO requestDTO, String id);
    ApiResponse<Void> delete(String id);
    UserResponseDTO findById(String id);
    List<UserResponseDTO> getAllUsers();
}
