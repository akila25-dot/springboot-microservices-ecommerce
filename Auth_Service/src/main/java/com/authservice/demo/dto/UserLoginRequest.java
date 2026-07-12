package com.authservice.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserLoginRequest(
        @NotBlank String username,
        @Email String email,   // ✅ optional email login
        @NotBlank String password
) {}
