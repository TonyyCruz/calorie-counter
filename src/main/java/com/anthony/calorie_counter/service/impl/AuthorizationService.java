package com.anthony.calorie_counter.service.impl;

import com.anthony.calorie_counter.infra.security.JwtService;
import com.anthony.calorie_counter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService{
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    public String authenticate(Authentication  authentication) {
        Authentication auth = authenticationManager.authenticate(authentication);
        return jwtService.generateToken(auth);
    }
}
