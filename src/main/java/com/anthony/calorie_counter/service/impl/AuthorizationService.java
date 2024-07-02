package com.anthony.calorie_counter.service.impl;

import com.anthony.calorie_counter.entity.User;
import com.anthony.calorie_counter.exceptions.EntityDataNotFoundException;
import com.anthony.calorie_counter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new EntityDataNotFoundException("User with email '%s' was not found.".formatted(username)));
    }
}
