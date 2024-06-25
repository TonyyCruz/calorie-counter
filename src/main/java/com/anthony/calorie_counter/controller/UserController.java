package com.anthony.calorie_counter.controller;

import com.anthony.calorie_counter.dto.request.UserDto;
import com.anthony.calorie_counter.dto.response.UserViewDto;
import com.anthony.calorie_counter.entity.User;
import com.anthony.calorie_counter.enums.UserRole;
import com.anthony.calorie_counter.service.impl.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

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
    ResponseEntity<UserViewDto> updateUser(@RequestBody @Valid UserDto userDto) {
        User user = userDto.toEntity();
        if (isAdmin()) user.setRole(UserRole.ADMIN);
        user.setId(getUserPrincipal().getId());
        user.setPassword(getUserPrincipal().getPassword());
        User updatedUser = userService.updateUser(user);
        return ResponseEntity.ok(new UserViewDto(updatedUser));
    }

//    @PutMapping("/update/user")
//    ResponseEntity<UserViewDto> updatePassword(@RequestBody @Valid PasswordUpdateDto passwordDto) {
//        User user = passwordDto.toEntity();
//        String email = SecurityContextHolder.getContext().getAuthentication().getName();
//        User updatedUser = userService.updateUser(email, user);
//        return ResponseEntity.ok(new UserViewDto(updatedUser));
//    }

//    @DeleteMapping
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    ResponseEntity<?> delete() {
//        String email = SecurityContextHolder.getContext().getAuthentication().getName();
//        userService.deleteByEmail(email);
//        return ResponseEntity.noContent().build();
//    }

    private boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Set<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
        return roles.contains("ROLE_ADMIN");
    }

    private User getUserPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }
}
