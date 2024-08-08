package com.anthony.calorie_counter.service.impl;

import com.anthony.calorie_counter.config.security.JwtService;
import com.anthony.calorie_counter.service.IAuthenticationService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements IAuthenticationService {
    private final JwtService jwtService;

    public AuthenticationService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public String authenticate(Authentication  authentication) {
        return jwtService.generateToken(authentication);
    }
}
