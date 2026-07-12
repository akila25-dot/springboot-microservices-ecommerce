package com.authservice.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegisterRequest(

    @NotBlank
    @Size(min = 3, max = 64)
    String username,

    @NotBlank
    @Email
    String email,

    @NotBlank
    @Size(min = 8, max = 128)
    String password,

    @NotBlank
    String role, // must be "USER" or "ADMIN"

    Boolean active // optional, defaults to true if not provided
) {}
