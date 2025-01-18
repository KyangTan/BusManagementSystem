package com.mingyang.busmanagementsystem.model.dto.request.user;

import java.io.Serializable;

public class UpdateUserRequestDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String password;
    private String role;
    private String email;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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