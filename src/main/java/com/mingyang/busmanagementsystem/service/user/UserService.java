package com.mingyang.busmanagementsystem.service.user;

import com.mingyang.busmanagementsystem.model.dto.request.user.CreateUserRequestDto;
import com.mingyang.busmanagementsystem.model.dto.request.user.GetUserListRequestDto;
import com.mingyang.busmanagementsystem.model.dto.request.user.UpdateUserRequestDto;
import com.mingyang.busmanagementsystem.model.entity.User;
import java.util.List;

public interface UserService {
    List<User> getUserList(GetUserListRequestDto requestDto);

    User getUserById(Long id);

    User createUser(CreateUserRequestDto requestDto);

    User updateUser(Long id, UpdateUserRequestDto requestDto);

    void deleteUser(Long id);
} 