package com.mingyang.busmanagementsystem.controller;

import com.mingyang.busmanagementsystem.model.dto.response.user.GetUserResponseDto;
import com.mingyang.busmanagementsystem.model.entity.User;
import com.mingyang.busmanagementsystem.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mingyang.busmanagementsystem.model.dto.request.user.CreateUserRequestDto;
import com.mingyang.busmanagementsystem.model.dto.request.user.GetUserListRequestDto;
import com.mingyang.busmanagementsystem.model.dto.request.user.UpdateUserRequestDto;
import com.mingyang.busmanagementsystem.model.dto.response.ApiResponse;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class UserControllerImpl {
    private final UserService userService;

    @Autowired
    public UserControllerImpl(UserService userService) {
        this.userService = userService;
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

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<GetUserResponseDto>> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity
                .ok(ApiResponse.success("User retrieved successfully", convertToDto(user)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<GetUserResponseDto>>> getUserList(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) String email) {
        GetUserListRequestDto requestDto = new GetUserListRequestDto();
        requestDto.setUsername(username);
        requestDto.setRole(role);
        requestDto.setEmail(email);

        List<User> users = userService.getUserList(requestDto);
        List<GetUserResponseDto> response = users.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success("Users retrieved successfully", response));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<GetUserResponseDto>> createUser(
            @RequestBody CreateUserRequestDto createUserRequestDto) {
        User user = userService.createUser(createUserRequestDto);
        return ResponseEntity.ok(ApiResponse.success("User created successfully", convertToDto(user)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<GetUserResponseDto>> updateUser(@PathVariable Long id,
            @RequestBody UpdateUserRequestDto updateUserRequestDto) {
        User user = userService.updateUser(id, updateUserRequestDto);
        return ResponseEntity.ok(ApiResponse.success("User updated successfully", convertToDto(user)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.success("User deleted successfully", null));
    }
}