package com.aliyara.smartshop.service.impl;

import com.aliyara.smartshop.dto.request.LoginRequestDTO;
import com.aliyara.smartshop.dto.request.RegisterRequestDTO;
import com.aliyara.smartshop.exception.ResourceNotFoundException;
import com.aliyara.smartshop.model.User;
import com.aliyara.smartshop.repository.UserRepository;
import com.aliyara.smartshop.service.interfaces.AuthService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    @Override
    public Optional<User> login(LoginRequestDTO requestDTO) {
        if(!userRepository.existsByEmail(requestDTO.getEmail())) {
            throw new ResourceNotFoundException("Email not exist!");
        }
        User user = userRepository.findByEmail(requestDTO.getEmail());

        if (BCrypt.checkpw(requestDTO.getPassword(), user.getPassword())) {
            return Optional.of(user);
        }
        return Optional.empty();
    }
}