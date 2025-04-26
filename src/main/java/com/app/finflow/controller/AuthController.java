package com.app.finflow.controller;

import com.app.finflow.config.JwtUtil;
import com.app.finflow.dto.AuthResponse;
import com.app.finflow.dto.GeneralDto;
import com.app.finflow.dto.LoginRequest;
import com.app.finflow.dto.RegisterRequest;
import com.app.finflow.repository.UserRepository;
import com.app.finflow.service.AuthService;

import com.app.finflow.service.MailService;
import com.app.finflow.serviceImpl.MailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public GeneralDto register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/request-password-reset")
    public ResponseEntity<GeneralDto> requestReset(@RequestParam String email) {
        return ResponseEntity.ok(authService.requestReset(email));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<GeneralDto> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        return ResponseEntity.ok(authService.resetPassword(token, newPassword));
    }

    @GetMapping("/verifyEmail")
    public ResponseEntity<GeneralDto> verifyUser(@RequestParam String token) {
        return ResponseEntity.ok(authService.verifyUser(token));
    }

}
