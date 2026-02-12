package org.ashan.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ashan.domain.dto.ApiJsonResponse;
import org.ashan.domain.dto.user.LoginRequest;
import org.ashan.domain.dto.user.LoginResponse;
import org.ashan.domain.dto.user.RegisterRequest;
import org.ashan.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("register")
    public ResponseEntity<ApiJsonResponse<String>> register(@Valid @RequestBody RegisterRequest registerUser){

        userService.registerUser(registerUser);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiJsonResponse<>(true,"User registered successfully",null));
    }

    @PostMapping("login")
    public ResponseEntity<ApiJsonResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest loginRequest){

        LoginResponse loginResponse = userService.login(loginRequest);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiJsonResponse<>(true,"User authenticated successfully",loginResponse));
    }
}
