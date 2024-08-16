package com.anthony.calorie_counter.service;

import org.springframework.security.core.Authentication;

public interface IAuthenticationService {
    String authenticate(Authentication authentication);
}
