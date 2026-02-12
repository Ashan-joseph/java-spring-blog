package org.ashan.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.ashan.domain.entity.AuthClient;
import org.ashan.service.JwtService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private String secretKey = null;

    @Override
    public String generateAccessToken(String clientId) {
        Map<String, Object> claims = new HashMap<>();

        return Jwts
                .builder()
                .claims()
                .add(claims)
                .subject(clientId)
                .issuer("Spring-Blog-App")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 8L * 60 * 60 * 1000))
                .and()
                .signWith(generateKey())
                .compact();
    }

    private SecretKey generateKey(){
        byte[] decode = Decoders.BASE64.decode(getSecretKey());
        return Keys.hmacShaKeyFor(decode);
    }

    public String getSecretKey(){
        return secretKey = "vjArvRHxD834sQNfZMDDMwtHCF8ULn2OtziB1n6t6w4=";
    }

    public String extractClientId(String token){
        return extractClaims(token, Claims::getSubject);
    }

    private <T>T extractClaims(String token, Function<Claims,T> claimresolver){
        Claims claims = extractClaims(token);
        return claimresolver.apply(claims);
    }

    private Claims extractClaims(String token){
        return Jwts
                .parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUsername(String token){
        return extractClaims(token, Claims::getSubject);
    }

    public Boolean isTokenValid(String token, UserDetails userDetails){
        final String userName = extractUsername(token);

        return userName.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token){
        return extractClaims(token, Claims::getExpiration);
    }
}
