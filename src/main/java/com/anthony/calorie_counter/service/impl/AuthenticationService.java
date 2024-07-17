package com.anthony.calorie_counter.service.impl;

import com.anthony.calorie_counter.infra.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private JwtService jwtService;

    public String authenticate(Authentication  authentication) {
        return jwtService.generateToken(authentication);
    }
}
