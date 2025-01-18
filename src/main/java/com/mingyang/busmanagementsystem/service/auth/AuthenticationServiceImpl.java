package com.mingyang.busmanagementsystem.service.auth;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mingyang.busmanagementsystem.model.dto.request.auth.LoginRequestDto;
import com.mingyang.busmanagementsystem.model.dto.request.auth.RegisterRequestDto;
import com.mingyang.busmanagementsystem.model.dto.response.auth.LoginResponseDto;
import com.mingyang.busmanagementsystem.model.entity.User;
import com.mingyang.busmanagementsystem.repository.UserRepository;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final Map<String, String> tokenStore = new HashMap<>(); // In production, use Redis or similar

    @Autowired
    public AuthenticationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public LoginResponseDto login(LoginRequestDto requestDto) {
        User user = userRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if (!user.getPassword().equals(hashPassword(requestDto.getPassword()))) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        // check if the user is already logged in
        if (tokenStore.containsValue(user.getUsername())) {
            String tokenString = tokenStore.entrySet().stream()
                    .filter(entry -> entry.getValue().equals(user.getUsername()))
                    .map(Map.Entry::getKey)
                    .findFirst()
                    .orElse(null);
            LoginResponseDto response = new LoginResponseDto();
            response.setToken(tokenString);
            response.setRole(user.getRole());
            response.setUsername(user.getUsername());
            return response;
        }

        String token = generateToken();
        tokenStore.put(token, user.getUsername());

        LoginResponseDto response = new LoginResponseDto();
        response.setToken(token);
        response.setRole(user.getRole());
        response.setUsername(user.getUsername());
        return response;
    }

    @Override
    public void logout(String token) {
        tokenStore.remove(token);
    }

    @Override
    public User register(RegisterRequestDto requestDto) {
        // Check if username already exists
        if (userRepository.findByUsername(requestDto.getUsername()).isPresent()) {
            throw new EntityExistsException("Username already exists");
        }

        if (requestDto.getUsername() == null || requestDto.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }

        if (requestDto.getRole() == null
                || !Arrays.asList("ADMIN", "DRIVER", "STUDENT", "STAFF").contains(requestDto.getRole())) {
            throw new IllegalArgumentException("Invalid role");
        }

        if (requestDto.getPassword() == null || requestDto.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        if (requestDto.getEmail() == null || requestDto.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        // existing email
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new EntityExistsException("Email already exists");
        }

        User user = new User();
        user.setUsername(requestDto.getUsername());
        user.setPassword(hashPassword(requestDto.getPassword()));
        user.setEmail(requestDto.getEmail());
        user.setRole(requestDto.getRole());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }

    @Override
    public User getCurrentUser(String token) {
        // Remove "Bearer " prefix if present
        token = token.replace("Bearer ", "");
        System.out.println("token: " + token);
        System.out.println("tokenStore: " + tokenStore);
        String username = tokenStore.get(token);
        if (username == null) {
            throw new IllegalArgumentException("Invalid or expired token");
        }

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}