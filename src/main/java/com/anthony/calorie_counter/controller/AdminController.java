package com.anthony.calorie_counter.controller;

import com.anthony.calorie_counter.dto.request.UserCreateDto;
import com.anthony.calorie_counter.dto.response.UserViewDto;
import com.anthony.calorie_counter.entity.User;
import com.anthony.calorie_counter.enums.UserRole;
import com.anthony.calorie_counter.service.impl.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/v1/admin")
public class AdminController {
    @Autowired
    UserService userService;

    @PostMapping("/register")
    ResponseEntity<UserViewDto> create(@RequestBody @Valid UserCreateDto userCreateDto, @RequestParam(name = "role") String role) {
        User user = userCreateDto.toEntity();
        user.setRole(UserRole.valueOf(role.toUpperCase()));
        User savedUser = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserViewDto(savedUser));
    }

    @GetMapping("/{id}")
    ResponseEntity<UserViewDto> findById(@PathVariable String id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(new UserViewDto(user));
    }

    @PutMapping("/{id}")
    ResponseEntity<UserViewDto> update(@PathVariable String id, @RequestBody @Valid UserCreateDto userCreateDto) {
        User user = userCreateDto.toEntity();
        User updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(new UserViewDto(updatedUser));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    ResponseEntity<?> delete(@PathVariable String id) {
        userService.deleteByEmail(id);
        return ResponseEntity.noContent().build();
    }
}
