package org.ashan.controller;

import lombok.RequiredArgsConstructor;
import org.ashan.service.impl.ExternalApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/external")
public class ExternalApiController {

    private final ExternalApiService externalApiService;

    @GetMapping
    public String getExternalApiResponse()
    {
        return externalApiService.callApi();
    }
}
