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

    @Autowired
    private MailServiceImpl mailService;

    @PostMapping("/register")
    public GeneralDto register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/request-password-reset")
    public ResponseEntity<?> requestReset(@RequestParam String email) {
        return userRepository.findByEmail(email)
                .map(user -> {
                    String token = jwtUtil.generateResetToken(email);
                    String resetLink = "http://localhost:5173/reset-password?token=" + token;

                    mailService.sendResetPasswordEmail(email, resetLink); // ✅

                    return ResponseEntity.ok("Reset link sent to " + email);
                })
                .orElseGet(() -> ResponseEntity.status(404).body("Email not registered"));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        if (!jwtUtil.validateResetToken(token)) {
            return ResponseEntity.status(400).body("Invalid or expired token");
        }

        String email = jwtUtil.extractEmail(token);
        return userRepository.findByEmail(email)
                .map(user -> {
                    user.setPassword(passwordEncoder.encode(newPassword));
                    userRepository.save(user);
                    return ResponseEntity.ok("Password updated successfully");
                })
                .orElseGet(() -> ResponseEntity.status(404).body("User not found"));
    }
}
