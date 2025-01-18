package com.mingyang.busmanagementsystem.service.auth;

import com.mingyang.busmanagementsystem.model.dto.request.auth.LoginRequestDto;
import com.mingyang.busmanagementsystem.model.dto.request.auth.RegisterRequestDto;
import com.mingyang.busmanagementsystem.model.dto.response.auth.LoginResponseDto;
import com.mingyang.busmanagementsystem.model.entity.User;

public interface AuthenticationService {
    LoginResponseDto login(LoginRequestDto requestDto);
    void logout(String token);
    User register(RegisterRequestDto requestDto);
    User getCurrentUser(String token);
} 