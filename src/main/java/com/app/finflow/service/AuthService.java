package com.app.finflow.service;

import com.app.finflow.dto.AuthResponse;
import com.app.finflow.dto.GeneralDto;
import com.app.finflow.dto.LoginRequest;
import com.app.finflow.dto.RegisterRequest;

public interface AuthService {
    GeneralDto register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}
