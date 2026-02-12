package org.ashan.config;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.ashan.service.JwtService;
import org.ashan.service.impl.ClientDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final ClientDetailsService clientDetailsService;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer")){
            filterChain.doFilter(request,response);
            return;
        }

        final String jwt = authHeader.substring(7);

        try {
            final String clientId = jwtService.extractClientId(jwt);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            UserDetails userDetails = null;
            if(clientId != null && authentication == null){
                userDetails = clientDetailsService.loadUserByUsername(clientId);
            }

            if(jwtService.isTokenValid(jwt,userDetails)){
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,null,userDetails.getAuthorities()
                );

                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }else{
                authenticationEntryPoint.commence(
                        request, response,
                        new org.springframework.security.authentication.BadCredentialsException("Invalid token")
                );
                return;
            }

            filterChain.doFilter(request,response);
        }catch(JwtException e){
            authenticationEntryPoint.commence(
                    request,response,
                    new org.springframework.security.authentication.BadCredentialsException("Invalid or expired token")
            );
        }
    }
}
