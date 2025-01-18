package com.mingyang.busmanagementsystem.aop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.mingyang.busmanagementsystem.model.entity.User;
import com.mingyang.busmanagementsystem.service.auth.AuthenticationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RoleBasedAccessInterceptor implements HandlerInterceptor {

    private final AuthenticationService authenticationService;

    @Autowired
    public RoleBasedAccessInterceptor(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws IllegalAccessException {

        // Skip auth routes
        if (request.getRequestURI().startsWith("/api/auth")) {
            return true;
        }

        // Allow OPTIONS requests for CORS
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }

        // Get token from header
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            throw new IllegalAccessException("No authorization token provided");
        }

        // Get current user
        User user = authenticationService.getCurrentUser(token);
        if (user == null) {
            throw new IllegalAccessException("Invalid token");
        }

        // Allow GET requests for all authenticated users
        if (request.getMethod().equals("GET")) {
            return true;
        }

        // DRIVER can only access Trip Record POST requests
        if (user.getRole().equals("DRIVER") && request.getMethod().equals("POST")
                && request.getRequestURI().contains("/api/trip-records")) {
            return true;
        }

        // Check if user is admin for POST, PUT, DELETE requests
        if (user.getRole().equals("ADMIN")) {
            return true;
        }

        throw new IllegalAccessException("Only administrators can perform this operation");
    }
}
