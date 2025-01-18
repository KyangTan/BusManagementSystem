package com.mingyang.busmanagementsystem.model.dto.request.user;

import java.io.Serializable;

public class GetUserListRequestDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private String role;
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
} 