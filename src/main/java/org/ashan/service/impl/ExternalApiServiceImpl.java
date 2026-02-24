package org.ashan.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ExternalApiServiceImpl implements ExternalApiService{

    private final WebClient webClient;

    public ExternalApiServiceImpl() {
        this.webClient = WebClient.builder().baseUrl("http://localhost:8081/api").build();
    }

    @Override
    public String callApi() {
        return webClient.get()
                .uri("/welcome")
                .retrieve()
                .bodyToMono(String.class)
                .block(); //
    }
}
