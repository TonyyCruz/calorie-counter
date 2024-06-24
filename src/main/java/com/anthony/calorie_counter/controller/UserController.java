package com.anthony.calorie_counter.controller;

import com.anthony.calorie_counter.dto.request.UserUpdateDto;
import com.anthony.calorie_counter.dto.response.UserViewDto;
import com.anthony.calorie_counter.entity.User;
import com.anthony.calorie_counter.service.impl.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    ResponseEntity<UserViewDto> findById(@PathVariable String id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(new UserViewDto(user));
    }

    @PutMapping("/update/user")
    ResponseEntity<UserViewDto> userUpdate(@RequestBody @Valid UserUpdateDto userUpdateDto) {
        User user = userUpdateDto.toEntity();
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User updatedUser = userService.updateUser(email, user);
        return ResponseEntity.ok(new UserViewDto(updatedUser));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    ResponseEntity<?> delete() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.deleteByEmail(email);
        return ResponseEntity.noContent().build();
    }
}
