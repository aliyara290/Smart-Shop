package com.aliyara.smartshop.service.impl;

import com.aliyara.smartshop.dto.request.UserRequestDTO;
import com.aliyara.smartshop.dto.response.UserResponseDTO;
import com.aliyara.smartshop.mapper.UserMapper;
import com.aliyara.smartshop.payload.ApiResponse;
import com.aliyara.smartshop.repository.UserRepository;
import com.aliyara.smartshop.service.interfaces.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDTO create(UserRequestDTO requestDTO) {
        return null;
    }

    @Override
    public UserResponseDTO update(UserRequestDTO requestDTO, UUID id) {
        return null;
    }

    @Override
    public ApiResponse<Void> delete(UUID id) {
        return null;
    }

    @Override
    public UserResponseDTO findById(UUID id) {
        return null;
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        return List.of();
    }
}
