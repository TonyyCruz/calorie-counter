package com.anthony.calorie_counter.controller;

import com.anthony.calorie_counter.dto.request.AuthenticationDto;
import com.anthony.calorie_counter.dto.request.UserDto;
import com.anthony.calorie_counter.dto.response.LoginResponseTokenDto;
import com.anthony.calorie_counter.dto.response.UserViewDto;
import com.anthony.calorie_counter.entity.User;
import com.anthony.calorie_counter.infra.security.TokenService;
import com.anthony.calorie_counter.service.impl.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseTokenDto> login(@RequestBody @Valid AuthenticationDto data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        String token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseTokenDto(token));
    }

    @PostMapping("/register")
    public ResponseEntity<UserViewDto> register(@RequestBody @Valid UserDto userDto) {
        String encryptedPassword = new BCryptPasswordEncoder().encode(userDto.password());
        User user = userDto.toEntity();
        user.setPassword(encryptedPassword);
        User registeredUser = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserViewDto(registeredUser));
    }
}
