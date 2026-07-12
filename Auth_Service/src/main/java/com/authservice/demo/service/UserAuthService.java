package com.authservice.demo.service;

import com.authservice.demo.dto.UserLoginRequest;
import com.authservice.demo.dto.UserRegisterRequest;
import com.authservice.demo.dto.UserTokenResponse;

public interface UserAuthService {
    UserTokenResponse processLoginRequest(UserLoginRequest request);
    void processRegistrationRequest(UserRegisterRequest request);
}
