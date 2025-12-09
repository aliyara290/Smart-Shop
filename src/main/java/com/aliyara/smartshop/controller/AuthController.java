package com.aliyara.smartshop.controller;

import com.aliyara.smartshop.dto.request.LoginRequestDTO;
import com.aliyara.smartshop.model.User;
import com.aliyara.smartshop.payload.ApiResponse;
import com.aliyara.smartshop.service.interfaces.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Void>> login(@Valid @RequestBody LoginRequestDTO requestDTO, HttpServletRequest request) {
        Optional<User> user = authService.login(requestDTO);

        HttpSession oldSession = request.getSession(false);
        if (oldSession != null) oldSession.invalidate();
        if(user.isPresent()) {
            HttpSession session = request.getSession(true);
            session.setAttribute("ROLE", user.get().getRole());
            session.setAttribute("ID", user.get().getId());
            session.setAttribute("NAME", user.get().getFirstName());
            session.setMaxInactiveInterval(60 * 60);
        }

        ApiResponse<Void> response = new ApiResponse<>(true, "Login successfully!", LocalDateTime.now(), null);
        return ResponseEntity.ok().body(response);
    }


    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }
        ApiResponse<Void> response = new ApiResponse<>(true, "Logout successfully!", LocalDateTime.now(), null);

        return ResponseEntity.ok().body(response);
    }
}
