package org.ashan.service.impl;

import lombok.RequiredArgsConstructor;
import org.ashan.domain.entity.AuthClient;
import org.ashan.repository.AuthRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientDetailsService implements UserDetailsService {

    private final AuthRepository authRepository;

    @Override
    public UserDetails loadUserByUsername(String clientId) throws UsernameNotFoundException{

        AuthClient client = authRepository.findByClientId(clientId)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid Client Credentials"));

        return User.withUsername(client.getClientId())
                .password(client.getSecret())
                .roles("OAUTH")
                .build();
    }
}
