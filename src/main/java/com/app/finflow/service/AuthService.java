package com.app.finflow.service;

import com.app.finflow.dto.AuthResponse;
import com.app.finflow.dto.LoginRequest;
import com.app.finflow.dto.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}
