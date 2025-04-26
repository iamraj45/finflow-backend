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
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;

import java.time.LocalDateTime;
import java.util.Optional;
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

    @Autowired
    private MailServiceImpl mailService;

    @Override
    public GeneralDto register(RegisterRequest request) {
        GeneralDto generalDto = new GeneralDto();

        if(null != request){
            User existingUser = userRepository.getUserByEmail(request.getEmail());
            if(null != existingUser){
                generalDto.setStatus(false);
                generalDto.setMessage("This email is already registered. Please try to login or register with a different email.");
                return generalDto;
            }
        }
        try {
            User user = new User();
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setLoginMethod("email");

            //For email verification
            String token = UUID.randomUUID().toString();
            user.setVerificationToken(token);
            user.setTokenExpiry(LocalDateTime.now().plusMinutes(5)); // 30 minutes
            user.setVerified(false);


            userRepository.save(user);

            //https://finflow-tracker.netlify.app
            String verificationLink = "http://localhost:5173/verifyEmail?token=" + token;
            mailService.sendVerificationEmail(user.getEmail(), "Verify Your Account",
                    "Click the link to verify: " + verificationLink);

            generalDto.setStatus(true);
            generalDto.setMessage("User registered successfully. Please check your email to verify your account.");
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

            User user = userRepository.getUserByEmail(request.getEmail());

            if (null == user) {
                throw new RuntimeException("Email not registered");
            }

            if (!user.isVerified()) {
                throw new RuntimeException("Please verify your email before logging in.");
            }
            if ("google".equals(user.getLoginMethod())) {
                throw new GoogleAccountLoginException("This account was created via Google Sign-In. Please log in with Google.");
            }

            try {
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
                );
            } catch (Exception e) {
                throw new RuntimeException("Invalid username or password");
            }


            String token = jwtUtil.generateToken(user);
            return new AuthResponse(token);
        }
    }

    @Override
    public GeneralDto requestReset(String email) {
        GeneralDto response = new GeneralDto();

       userRepository.findByEmail(email)
                .map(user -> {
                    String token = jwtUtil.generateResetToken(email);
                    String resetLink = "https://finflow-tracker.netlify.app/reset-password?token=" + token;

                    mailService.sendResetPasswordEmail(email, resetLink); // ✅

                    return ResponseEntity.ok("Reset link sent to " + email);
                })
                .orElseGet(() -> ResponseEntity.status(404).body("Email not registered"));

       return response;
    }

    @Override
    public GeneralDto resetPassword(String token, String newPassword) {
        GeneralDto response = new GeneralDto();

        if (!jwtUtil.validateResetToken(token)) {
            response.setStatus(false);
            response.setMessage("Invalid or expired token");
            return response;
        }

        String email = jwtUtil.extractEmail(token);
        userRepository.findByEmail(email)
                .map(user -> {
                    user.setPassword(passwordEncoder.encode(newPassword));
                    userRepository.save(user);
                    response.setStatus(true);
                    response.setMessage("Password updated successfully");
                    return response;
                })
                .orElseGet(() -> {
                    response.setStatus(false);
                    response.setMessage("Email not registered");
                    return response;
                });

        return response;

    }

    @Override
    public GeneralDto verifyUser(String token) {
        GeneralDto response = new GeneralDto();
        response.setStatus(true);
        response.setMessage("Account verified successfully");

        Optional<User> optionalUser = userRepository.findByVerificationToken(token);
        if (optionalUser.isEmpty()) {
            response.setStatus(false);
            response.setMessage("Invalid token");
            return response;
        }

        User user = optionalUser.get();
        if (user.getTokenExpiry().isBefore(LocalDateTime.now())){
            response.setStatus(false);
            response.setMessage("Token expired");
            return response;
        }

        user.setVerified(true);
        user.setVerificationToken(null); // Invalidate token
        user.setTokenExpiry(null);
        userRepository.save(user);

        return response;
    }

}
