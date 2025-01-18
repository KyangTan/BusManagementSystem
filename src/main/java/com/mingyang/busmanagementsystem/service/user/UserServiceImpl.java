package com.mingyang.busmanagementsystem.service.user;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mingyang.busmanagementsystem.model.dto.request.user.CreateUserRequestDto;
import com.mingyang.busmanagementsystem.model.dto.request.user.GetUserListRequestDto;
import com.mingyang.busmanagementsystem.model.dto.request.user.UpdateUserRequestDto;
import com.mingyang.busmanagementsystem.model.entity.User;
import com.mingyang.busmanagementsystem.repository.UserRepository;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.security.MessageDigest;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getUserList(GetUserListRequestDto requestDto) {
        if (requestDto == null) {
            return userRepository.findAll();
        }

        return userRepository.findByFilters(
                requestDto.getUsername(),
                requestDto.getRole(),
                requestDto.getEmail());
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    @Override
    public User createUser(CreateUserRequestDto requestDto) {
        validateUserCreation(requestDto);

        User user = new User();
        user.setUsername(requestDto.getUsername());
        user.setPassword(hashPassword(requestDto.getPassword()));
        user.setRole(requestDto.getRole());
        user.setEmail(requestDto.getEmail());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    private void validateUserCreation(CreateUserRequestDto requestDto) {
        // Check if username already exists
        if (userRepository.findByUsername(requestDto.getUsername()).isPresent()) {
            throw new EntityExistsException("Username already exists");
        }

        // Check if role is valid
        if (!Arrays.asList("ADMIN", "DRIVER", "STUDENT", "STAFF").contains(requestDto.getRole())) {
            throw new IllegalArgumentException("Invalid role. Role must be ADMIN, DRIVER, or STUDENT");
        }

        // Validate required fields
        if (requestDto.getUsername() == null || requestDto.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username is required");
        }
        if (requestDto.getPassword() == null || requestDto.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }
        if (requestDto.getEmail() == null || requestDto.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
    }

    @Override
    @Transactional
    public User updateUser(Long id, UpdateUserRequestDto requestDto) {
        User user = userRepository.findByIdWithPessimisticLock(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

        validateUserUpdate(requestDto);

        if (requestDto.getPassword() != null) {
            user.setPassword(requestDto.getPassword()); // In real application, password should be encrypted
        }
        if (requestDto.getRole() != null) {
            user.setRole(requestDto.getRole());
        }
        if (requestDto.getEmail() != null) {
            user.setEmail(requestDto.getEmail());
        }

        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    private void validateUserUpdate(UpdateUserRequestDto requestDto) {
        if (requestDto.getRole() != null &&
                !Arrays.asList("ADMIN", "DRIVER", "STUDENT", "STAFF").contains(requestDto.getRole())) {
            throw new IllegalArgumentException("Invalid role. Role must be ADMIN, DRIVER, or STUDENT");
        }
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findByIdWithPessimisticLock(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        userRepository.delete(user);
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