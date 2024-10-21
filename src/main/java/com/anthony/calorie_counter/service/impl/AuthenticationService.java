package com.anthony.calorie_counter.service.impl;

import com.anthony.calorie_counter.config.security.JwtService;
import com.anthony.calorie_counter.service.interfaces.IAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {
    private final JwtService jwtService;

    @Override
    public String authenticate(Authentication  authentication) {
        return jwtService.generateToken(authentication);
    }
}
