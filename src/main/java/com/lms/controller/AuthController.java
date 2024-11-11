package com.lms.controller;

import com.lms.security.model.AuthenticationRequest;
import com.lms.security.util.JWTUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final JWTUtil jwtUtil;

    public AuthController(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthenticationRequest loginRequest) {
        // Authenticate user (check username/password)
        // For simplicity, let's assume the user is valid
        return jwtUtil.generateToken(loginRequest.getEmail());
    }
}