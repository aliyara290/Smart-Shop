package com.aliyara.smartshop.service.interfaces;

import com.aliyara.smartshop.dto.request.LoginRequestDTO;
import com.aliyara.smartshop.model.User;

import java.util.Optional;

public interface AuthService {
    Optional<User> login(LoginRequestDTO requestDTO);
}