package com.authservice.demo.service;

import com.authservice.demo.dto.UserLoginRequest;
import com.authservice.demo.dto.UserRegisterRequest;
import com.authservice.demo.dto.UserTokenResponse;
import com.authservice.demo.exception.AuthErrorCode;
import com.authservice.demo.exception.UserAuthException;
import com.authservice.demo.model.UserAccount;
import com.authservice.demo.repository.UserAccountRepository;
import com.authservice.demo.security.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAuthServiceImpl implements UserAuthService {

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public UserAuthServiceImpl(UserAccountRepository userAccountRepository,
                               PasswordEncoder passwordEncoder,
                               JwtTokenProvider jwtTokenProvider) {
        this.userAccountRepository = userAccountRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * Handles user registration: validates, encodes password, and saves account.
     */
    @Override
    public void processRegistrationRequest(UserRegisterRequest request) {
        // Check if username already exists
        if (userAccountRepository.findByUsername(request.username()).isPresent()) {
            throw new UserAuthException(
                AuthErrorCode.REGISTRATION_FAILED,
                "Username already exists: " + request.username(),
                HttpStatus.CONFLICT.value()
            );
        }

        // Check if email already exists
        if (userAccountRepository.findByEmail(request.email()).isPresent()) {
            throw new UserAuthException(
                AuthErrorCode.REGISTRATION_FAILED,
                "Email already exists: " + request.email(),
                HttpStatus.CONFLICT.value()
            );
        }

       
        try {
            // Normalize role to Spring Security format: ROLE_USER / ROLE_ADMIN
            String normalizedRole = normalizeRole(request.role());

            UserAccount account = UserAccount.builder()
                    .username(request.username())
                    .email(request.email())
                    .passwordHash(passwordEncoder.encode(request.password())) // encode raw password
                    .role(normalizedRole)
                    .active(request.active() != null ? request.active() : true)
                    .build();

            userAccountRepository.save(account);
        } catch (Exception e) {
            throw new UserAuthException(
                AuthErrorCode.REGISTRATION_FAILED,
                "Failed to register user: " + e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
            );
        }
    }

    /**
     * Handles login: validates password, checks active, issues JWT.
     */
    @Override
    public UserTokenResponse processLoginRequest(UserLoginRequest request) {
        UserAccount user = userAccountRepository.findByUsername(request.username())
            .orElseThrow(() -> new UserAuthException(
                AuthErrorCode.USER_NOT_FOUND,
                "User not found: " + request.username(),
                HttpStatus.UNAUTHORIZED.value()
            ));

        

        // Validate password
        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new UserAuthException(
                AuthErrorCode.INVALID_CREDENTIALS,
                "Invalid username or password",
                HttpStatus.UNAUTHORIZED.value()
            );
        }

        // Generate JWT
        String token;
        try {
            token = jwtTokenProvider.issueAccessToken(user.getUsername(), user.getRole());
        } catch (Exception e) {
            throw new UserAuthException(
                AuthErrorCode.TOKEN_GENERATION_FAILED,
                "Failed to generate authentication token",
                HttpStatus.INTERNAL_SERVER_ERROR.value()
            );
        }

        // Return response (1 hour expiry)
        return new UserTokenResponse(token, "Bearer", 3600);
    }

    private String normalizeRole(String role) {
        if (role == null || role.isBlank()) {
            return "ROLE_USER";
        }
        String r = role.trim().toUpperCase();
        return r.startsWith("ROLE_") ? r : "ROLE_" + r;
    }
}
