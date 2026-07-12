package com.authservice.demo.controller;

import com.authservice.demo.dto.UserRegisterRequest;
import com.authservice.demo.service.UserAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")   // ✅ keep versioned path
@RequiredArgsConstructor
public class UserAuthController {

    private final UserAuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegisterRequest request) {
        authService.processRegistrationRequest(request);

        return ResponseEntity.ok(Map.of(
            "message", "Registration successful",
            "username", request.username(),
            "role", request.role(),
            "active", request.active() != null ? request.active() : true
        ));
    }
    
    

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody com.authservice.demo.dto.UserLoginRequest request) {
        var tokenResponse = authService.processLoginRequest(request);
        return ResponseEntity.ok(tokenResponse);
    }
}
