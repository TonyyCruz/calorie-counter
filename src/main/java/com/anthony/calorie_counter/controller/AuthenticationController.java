package com.anthony.calorie_counter.controller;

import com.anthony.calorie_counter.dto.request.user.AuthenticationDto;
import com.anthony.calorie_counter.dto.request.user.UserCreateDto;
import com.anthony.calorie_counter.dto.response.user.LoginResponseTokenDto;
import com.anthony.calorie_counter.dto.response.user.UserViewDto;
import com.anthony.calorie_counter.entity.UserModel;
import com.anthony.calorie_counter.service.impl.AuthenticationService;
import com.anthony.calorie_counter.service.impl.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseTokenDto> login(@RequestBody AuthenticationDto authData) {
        String token = authenticationService.authenticate(authData.toAuthentication());
        return ResponseEntity.ok(new LoginResponseTokenDto(token));
    }
}
