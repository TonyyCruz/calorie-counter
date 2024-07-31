package com.anthony.calorie_counter.service.impl;

import com.anthony.calorie_counter.config.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final JwtService jwtService;

    public AuthenticationService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public String authenticate(Authentication  authentication) {
        return jwtService.generateToken(authentication);
    }
}
