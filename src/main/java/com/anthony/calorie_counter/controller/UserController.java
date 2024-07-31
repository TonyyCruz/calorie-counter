package com.anthony.calorie_counter.controller;

import com.anthony.calorie_counter.dto.request.user.PasswordUpdateDto;
import com.anthony.calorie_counter.dto.request.user.UserUpdateDto;
import com.anthony.calorie_counter.dto.response.user.UserViewDto;
import com.anthony.calorie_counter.entity.UserModel;
import com.anthony.calorie_counter.exceptions.AuthenticationDataException;
import com.anthony.calorie_counter.service.impl.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
        UserModel userModel = userService.findById(id);
        return ResponseEntity.ok(new UserViewDto(userModel));
    }

    @PutMapping("/update/user")
    ResponseEntity<UserViewDto> updateUser(@RequestBody @Valid UserUpdateDto userUpdateDto) {
        System.out.println("-----update------");
        
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        UserModel userModel = userService.updateUser(getUserPrincipal().getId(), userUpdateDto.toEntity());
        return ResponseEntity.ok(new UserViewDto(userModel));
    }

    @PutMapping("/update/password")
    ResponseEntity<String> updatePassword(@RequestBody @Valid PasswordUpdateDto passwordDto) {
        if (!passwordEncoder.matches(passwordDto.getOldPassword(), getUserPrincipal().getPassword())) {
            throw new AuthenticationDataException("Old password is incorrect.");
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

    private UserModel getUserPrincipal() {
        return (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
