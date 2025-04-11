package com.app.finflow.serviceImpl;

import com.app.finflow.dto.AuthResponse;
import com.app.finflow.dto.GeneralDto;
import com.app.finflow.dto.LoginRequest;
import com.app.finflow.dto.RegisterRequest;
import com.app.finflow.model.User;
import com.app.finflow.repository.UserRepository;
import com.app.finflow.service.AuthService;
import com.app.finflow.config.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public GeneralDto register(RegisterRequest request) {
        GeneralDto generalDto = new GeneralDto();

        if(null != request){
            User existingUser = userRepository.getUserByEmail(request.getEmail());
            if(null != existingUser){
                generalDto.setStatus(false);
                generalDto.setMessage("This email is already registered. Please try logging in or use a different email.");
                return generalDto;
            }
        }
        try {
            User user = new User();
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            userRepository.save(user);
            generalDto.setStatus(true);
            generalDto.setMessage("User registered successfully");
        } catch (Exception e) {
            generalDto.setStatus(false);
            generalDto.setMessage("Error during user registration");
        }
        return generalDto;
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        String token = jwtUtil.generateToken(user);
        return new AuthResponse(token);
    }
}
