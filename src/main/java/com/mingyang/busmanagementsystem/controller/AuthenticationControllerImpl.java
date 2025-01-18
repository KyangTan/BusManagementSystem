package com.mingyang.busmanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mingyang.busmanagementsystem.model.dto.request.auth.LoginRequestDto;
import com.mingyang.busmanagementsystem.model.dto.request.auth.RegisterRequestDto;
import com.mingyang.busmanagementsystem.model.dto.response.ApiResponse;
import com.mingyang.busmanagementsystem.model.dto.response.auth.LoginResponseDto;
import com.mingyang.busmanagementsystem.model.dto.response.user.GetUserResponseDto;
import com.mingyang.busmanagementsystem.model.entity.User;
import com.mingyang.busmanagementsystem.service.auth.AuthenticationService;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationControllerImpl {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationControllerImpl(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> login(@RequestBody LoginRequestDto requestDto) {
        LoginResponseDto response = authenticationService.login(requestDto);
        return ResponseEntity.ok(ApiResponse.success("Login successful", response));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(@RequestHeader("Authorization") String token) {
        authenticationService.logout(token);
        return ResponseEntity.ok(ApiResponse.success("Logout successful", null));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<GetUserResponseDto>> getMe(@RequestHeader("Authorization") String token) {
        User user = authenticationService.getCurrentUser(token);
        GetUserResponseDto response = convertToDto(user);
        return ResponseEntity.ok(ApiResponse.success("User retrieved successfully", response));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<GetUserResponseDto>> register(@RequestBody RegisterRequestDto requestDto) {
        User user = authenticationService.register(requestDto);
        GetUserResponseDto response = convertToDto(user);
        return ResponseEntity.ok(ApiResponse.success("Register successful", response));
    }

    private GetUserResponseDto convertToDto(User user) {
        if (user == null)
            return null;
        GetUserResponseDto dto = new GetUserResponseDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setRole(user.getRole());
        dto.setEmail(user.getEmail());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        return dto;
    }
}
