package org.ashan.service;


import org.ashan.domain.dto.AuthRequest;
import org.ashan.domain.dto.AuthResponse;
import org.ashan.domain.entity.AuthClient;

public interface AuthService {

    AuthResponse authenticateClient(AuthRequest authRequest);
}
