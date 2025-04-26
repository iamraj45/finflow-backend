package com.app.finflow.service;

import com.app.finflow.dto.AuthResponse;
import com.app.finflow.dto.GeneralDto;
import com.app.finflow.dto.LoginRequest;
import com.app.finflow.dto.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

public interface AuthService {
    GeneralDto register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
    GeneralDto requestReset(String email);
    GeneralDto resetPassword(String token, String newPassword);
    GeneralDto verifyUser(String token);
}
