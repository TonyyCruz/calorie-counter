package com.anthony.calorie_counter.controller;

import com.anthony.calorie_counter.dto.request.user.PasswordUpdateDto;
import com.anthony.calorie_counter.dto.request.user.UserCreateDto;
import com.anthony.calorie_counter.dto.request.user.UserUpdateDto;
import com.anthony.calorie_counter.dto.response.user.UserViewDto;
import com.anthony.calorie_counter.entity.UserModel;
import com.anthony.calorie_counter.enums.UserRole;
import com.anthony.calorie_counter.exceptions.AuthenticationDataException;
import com.anthony.calorie_counter.service.impl.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController @RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<UserViewDto> create(@RequestBody @Valid UserCreateDto userCreateDto) {
        UserModel userModel = userCreateDto.toEntity();
        UserModel registeredUserModel = userService.create(UserRole.ROLE_USER, userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserViewDto(registeredUserModel));
    }

    @GetMapping("/{id}")
    ResponseEntity<UserViewDto> findById(@PathVariable String id) {
        UserModel userModel = userService.findById(UUID.fromString(id));
        return ResponseEntity.ok(new UserViewDto(userModel));
    }

    @PutMapping("/update/user")
    ResponseEntity<UserViewDto> updateUser(@RequestBody @Valid UserUpdateDto userUpdateDto) {
        UserModel userModel = userService.updateUser(getPrincipalUsername(), userUpdateDto.toEntity());
        return ResponseEntity.ok(new UserViewDto(userModel));
    }

    @PutMapping("/update/password")
    ResponseEntity<String> updatePassword(@RequestBody @Valid PasswordUpdateDto passwordDto) {
        UserModel user = userService.loadUserByUsername(getPrincipalUsername());
        if (!passwordEncoder.matches(passwordDto.getOldPassword(), user.getPassword())) {
            throw new AuthenticationDataException("Old password is incorrect.");
        }
        userService.updatePassword(user.getId(), passwordDto.getNewPassword());
        return ResponseEntity.ok("Password updated successfully.");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    ResponseEntity<?> delete(@PathVariable String id) {
        userService.delete(getPrincipalUsername(), UUID.fromString(id));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/register/admin")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public ResponseEntity<UserViewDto> createAdmin(
            @RequestBody @Valid UserCreateDto userCreateDto, @RequestParam(name = "role") String role
    ) {
        UserModel userModel = userCreateDto.toEntity();
        UserModel registeredUserModel = userService.create(UserRole.getRoleFrom(role), userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserViewDto(registeredUserModel));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public ResponseEntity<Page<UserViewDto>> listAll(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<UserViewDto> users = userService.findAll(pageable).map(UserViewDto::new);
        return ResponseEntity.ok(users);
    }

    @PutMapping("/update/user/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    ResponseEntity<UserViewDto> updateUserById(@RequestBody @Valid UserUpdateDto userUpdateDto, @PathVariable String id) {
        UserModel userModel = userService.updateUser(UUID.fromString(id), userUpdateDto.toEntity());
        return ResponseEntity.ok(new UserViewDto(userModel));
    }

    private String getPrincipalUsername() {
        Jwt jwtUser = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return jwtUser.getSubject();
    }
}
