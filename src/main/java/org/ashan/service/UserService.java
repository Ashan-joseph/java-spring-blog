package org.ashan.service;

import org.ashan.domain.dto.user.LoginRequest;
import org.ashan.domain.dto.user.LoginResponse;
import org.ashan.domain.dto.user.RegisterRequest;

public interface UserService {

    void registerUser(RegisterRequest registerUser);

    LoginResponse login(LoginRequest loginRequest);

    void sendUserWelcomeEmail();
}
