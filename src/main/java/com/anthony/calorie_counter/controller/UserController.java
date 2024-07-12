package com.anthony.calorie_counter.controller;

import com.anthony.calorie_counter.dto.request.user.PasswordUpdateDto;
import com.anthony.calorie_counter.dto.request.user.UserUpdateDto;
import com.anthony.calorie_counter.dto.response.user.UserViewDto;
import com.anthony.calorie_counter.entity.User;
import com.anthony.calorie_counter.exceptions.AuthenticationDataException;
import com.anthony.calorie_counter.service.impl.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/{id}")
    ResponseEntity<UserViewDto> findById(@PathVariable String id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(new UserViewDto(user));
    }

    @PutMapping("/update/user")
    ResponseEntity<UserViewDto> updateUser(@RequestBody @Valid UserUpdateDto userUpdateDto) {
        User user = userUpdateDto.toEntity();
        User updatedUser = userService.updateUser(getUserPrincipal().getId(), user);
        return ResponseEntity.ok(new UserViewDto(updatedUser));
    }

    @PutMapping("/update/password")
    ResponseEntity<String> updatePassword(@RequestBody @Valid PasswordUpdateDto passwordDto) {
        boolean passwordMatches = passwordEncoder.matches(passwordDto.getOldPassword(), getUserPrincipal().getPassword());
        if (!passwordMatches) {
            throw new AuthenticationDataException("Old password is invalid.");
        }
        userService.updatePassword(getUserPrincipal().getId(), passwordDto.getNewPassword());
        return ResponseEntity.ok("Update successfully.");
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    ResponseEntity<?> delete() {
        userService.deleteById(getUserPrincipal().getId());
        return ResponseEntity.noContent().build();
    }

    private User getUserPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }
}
