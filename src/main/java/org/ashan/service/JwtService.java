package org.ashan.service;

import org.ashan.domain.entity.AuthClient;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    String generateAccessToken(String clientId);

    String extractClientId(String token);

    Boolean isTokenValid(String token, UserDetails userDeatils);
}
