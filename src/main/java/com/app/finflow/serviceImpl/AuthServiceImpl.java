package com.app.finflow.serviceImpl;

import com.app.finflow.dto.AuthResponse;
import com.app.finflow.dto.GeneralDto;
import com.app.finflow.dto.LoginRequest;
import com.app.finflow.dto.RegisterRequest;
import com.app.finflow.exception.GoogleAccountLoginException;
import com.app.finflow.model.User;
import com.app.finflow.repository.UserRepository;
import com.app.finflow.service.AuthService;
import com.app.finflow.config.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;

import java.util.UUID;


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
            user.setLoginMethod("email");
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
        if (request.getFirebaseToken() != null && !request.getFirebaseToken().isBlank()) {
            // Firebase login path
            try {
                FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(request.getFirebaseToken());
                String email = firebaseToken.getEmail();
                String name = firebaseToken.getName();

                // Find or create user
                User user = userRepository.findByEmail(email).orElseGet(() -> {
                    User newUser = new User();
                    newUser.setEmail(email);
                    newUser.setName(name);
                    newUser.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
                    newUser.setLoginMethod("google");
                    return userRepository.save(newUser);
                });

                String token = jwtUtil.generateToken(user);
                return new AuthResponse(token);

            } catch (Exception e) {
                throw new RuntimeException("Invalid Firebase token", e);
            }

        } else {
            // Email/password login
            User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
            if ("google".equals(user.getLoginMethod())) {
                throw new GoogleAccountLoginException("This account was created via Google Sign-In. Please log in with Google.");
            }
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            String token = jwtUtil.generateToken(user);
            return new AuthResponse(token);
        }
    }

}
