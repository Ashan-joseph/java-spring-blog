package org.ashan.service.impl;

import lombok.RequiredArgsConstructor;
import org.ashan.domain.dto.ApiJsonResponse;
import org.ashan.domain.dto.AuthRequest;
import org.ashan.domain.dto.AuthResponse;
import org.ashan.domain.entity.AuthClient;
import org.ashan.repository.AuthRepository;
import org.ashan.service.AuthService;
import org.ashan.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final AuthRepository authRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public AuthResponse authenticateClient(AuthRequest authRequest) {
        Authentication auth =  authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getClientId(),authRequest.getSecret())
        );

        String accessToken = jwtService.generateAccessToken(authRequest.getClientId());

        System.out.println(System.currentTimeMillis());
        AuthResponse authResponse = new AuthResponse();
        authResponse.setTokenType("Bearer");
        authResponse.setExpireAt(System.currentTimeMillis() + (8L * 60 * 60 * 1000));
        authResponse.setAccessToken(accessToken);

        return authResponse;
    }
}
