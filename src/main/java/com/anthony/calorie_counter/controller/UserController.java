package com.anthony.calorie_counter.controller;

import com.anthony.calorie_counter.entity.dto.request.user.PasswordAuthenticateDto;
import com.anthony.calorie_counter.entity.dto.request.user.PasswordUpdateDto;
import com.anthony.calorie_counter.entity.dto.request.user.UserCreateDto;
import com.anthony.calorie_counter.entity.dto.request.user.UserUpdateDto;
import com.anthony.calorie_counter.entity.dto.response.user.UserViewDto;
import com.anthony.calorie_counter.entity.UserModel;
import com.anthony.calorie_counter.enums.UserRole;
import com.anthony.calorie_counter.exceptions.ForbiddenRequestException;
import com.anthony.calorie_counter.exceptions.InvalidCredentialsException;
import com.anthony.calorie_counter.exceptions.messages.ExceptionMessages;
import com.anthony.calorie_counter.service.interfaces.IUserService;
import com.anthony.calorie_counter.service.impl.UserService;
import com.anthony.calorie_counter.utils.UserAuthorizationManager;
import jakarta.validation.Valid;
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

import java.util.Arrays;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final IUserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<UserViewDto> create(@RequestBody @Valid UserCreateDto userCreateDto) {
        UserModel registeredUserModel = userService.create(userCreateDto.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserViewDto(registeredUserModel));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserViewDto> findUserById(@PathVariable UUID id) {
        UserAuthorizationManager.checkAuthorization(id);
        UserModel userModel = userService.findById(id);
        return ResponseEntity.ok(new UserViewDto(userModel));
    }

    @PutMapping("/update/user/{id}")
    public ResponseEntity<UserViewDto> updateUserById(
            @PathVariable UUID id,
            @RequestBody @Valid UserUpdateDto userUpdateDto
    ) {
        UserAuthorizationManager.checkAuthorization(id);
        UserModel userModel = userService.updateUser(id, userUpdateDto.toEntity());
        return ResponseEntity.ok(new UserViewDto(userModel));
    }

    @PutMapping("/update/password/{id}")
    public ResponseEntity<String> updatePassword(
            @PathVariable UUID id,
            @RequestBody @Valid PasswordUpdateDto passwordUpdateDto
    ) {
        UserModel user = userService.findById(id);
        if (!passwordEncoder.matches(passwordUpdateDto.getOldPassword(), user.getPassword())
                && UserAuthorizationManager.principalIsNotAdmin()
        ) {
            throw new InvalidCredentialsException(ExceptionMessages.INCORRECT_USER_DATA);
        }
        UserAuthorizationManager.checkAuthorization(id);
        userService.updatePassword(id, passwordUpdateDto.getNewPassword());
        return ResponseEntity.ok("Password updated successfully.");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> delete(@PathVariable UUID id, @RequestBody PasswordAuthenticateDto passwordAuthenticate) {
        UserModel user = userService.findById(id);
        if (!passwordEncoder.matches(passwordAuthenticate.getPassword(), user.getPassword())
                && UserAuthorizationManager.principalIsNotAdmin()) {
            throw new InvalidCredentialsException(ExceptionMessages.INCORRECT_USER_DATA);
        }
        UserAuthorizationManager.checkAuthorization(id);
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/promote/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public ResponseEntity<UserViewDto> promoteToAdmin(@PathVariable(name = "id") String id) {
        UserModel user = userService.promoteToAdmin(UUID.fromString(id));
        return ResponseEntity.ok(new UserViewDto(user));
    }

    @PostMapping("/demote/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public ResponseEntity<UserViewDto> demoteFromAdmin(@PathVariable(name = "id") String id) {
        UserModel updatedUser = userService.demoteFromAdmin(UUID.fromString(id));
        return ResponseEntity.ok(new UserViewDto(updatedUser));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public ResponseEntity<Page<UserViewDto>> listAll(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<UserViewDto> users = userService.findAll(pageable).map(UserViewDto::new);
        return ResponseEntity.ok(users);
    }

//    private Jwt getJwtUser() {
//        return (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//    }
//
//    private UUID getPrincipalId() {
//        return UUID.fromString(getJwtUser().getSubject());
//    }
//
//    private boolean principalIsNotAdmin() {
//        String[] roles = getJwtUser().getClaims().get("scope").toString().split(" ");
//        return !Arrays.asList(roles).contains(UserRole.ROLE_ADMIN.name());
//    }
//
//    private void checkAuthorization(String id) {
//        if (principalIsNotAdmin() && !getPrincipalId().equals(UUID.fromString(id))) {
//            throw new ForbiddenRequestException(ExceptionMessages.UNAUTHORIZED_TO_PERFORM_ACTION);
//        }
//    }
}
