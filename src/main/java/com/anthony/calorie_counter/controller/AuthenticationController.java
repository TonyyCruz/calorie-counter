package com.anthony.calorie_counter.controller;

import com.anthony.calorie_counter.dto.response.user.LoginResponseTokenDto;
import com.anthony.calorie_counter.service.IAuthenticationService;
import com.anthony.calorie_counter.service.impl.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final IAuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseTokenDto> login(Authentication authentication) {
        String token = authenticationService.authenticate(authentication);
        return ResponseEntity.ok(new LoginResponseTokenDto(token));
    }
}
