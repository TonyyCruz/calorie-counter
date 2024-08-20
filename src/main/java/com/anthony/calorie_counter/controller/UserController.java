package com.anthony.calorie_counter.controller;

import com.anthony.calorie_counter.dto.request.user.PasswordUpdateDto;
import com.anthony.calorie_counter.dto.request.user.UserCreateDto;
import com.anthony.calorie_counter.dto.request.user.UserUpdateDto;
import com.anthony.calorie_counter.dto.response.user.UserViewDto;
import com.anthony.calorie_counter.entity.RoleModel;
import com.anthony.calorie_counter.entity.UserModel;
import com.anthony.calorie_counter.enums.UserRole;
import com.anthony.calorie_counter.exceptions.AuthenticationDataException;
import com.anthony.calorie_counter.exceptions.UnauthorizedRequest;
import com.anthony.calorie_counter.service.IUserService;
import com.anthony.calorie_counter.service.impl.UserService;
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

@RestController @RequestMapping("/api/v1/users")
public class UserController {
    private final IUserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<UserViewDto> create(@RequestBody @Valid UserCreateDto userCreateDto) {
        UserModel userModel = userCreateDto.toEntity();
        UserModel registeredUserModel = userService.create(userModel);
        UserViewDto view = new UserViewDto(userModel);
        boolean t = view.roles().contains(new RoleModel(UserRole.ROLE_USER));
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserViewDto(registeredUserModel));
    }

    @GetMapping
    public ResponseEntity<UserViewDto> findSelfData() {
        UserModel userModel = userService.findById(getPrincipalId());
        return ResponseEntity.ok(new UserViewDto(userModel));
    }

    @PutMapping("/update/user")
    public ResponseEntity<UserViewDto> updateUser(@RequestBody @Valid UserUpdateDto userUpdateDto) {
        UserModel userModel = userService.updateUser(getPrincipalId(), userUpdateDto.toEntity());
        return ResponseEntity.ok(new UserViewDto(userModel));
    }

    @PutMapping("/update/password")
    public ResponseEntity<String> updatePassword(@RequestBody @Valid PasswordUpdateDto passwordDto) {
        UserModel user = userService.findById(getPrincipalId());
        if (!passwordEncoder.matches(passwordDto.getOldPassword(), user.getPassword())) {
            throw new AuthenticationDataException("Old password is incorrect.");
        }
        userService.updatePassword(user.getId(), passwordDto.getNewPassword());
        return ResponseEntity.ok("Password updated successfully.");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> delete(@PathVariable String id) {
        if(isPrincipalAdmin() || getPrincipalId().equals(UUID.fromString(id))) {
            userService.deleteById(UUID.fromString(id));
        } else {
            throw new UnauthorizedRequest("You have no authorization to delete another user.");
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public ResponseEntity<UserViewDto> findUserById(@PathVariable String id) {
        UserModel userModel = userService.findById(UUID.fromString(id));
        return ResponseEntity.ok(new UserViewDto(userModel));
    }

    @PostMapping("/promote/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public ResponseEntity<UserViewDto> promoteToAdmin(@PathVariable(name = "id") String id) {
        UserModel user = userService.promoteToAdmin(UUID.fromString(id));
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserViewDto(user));
    }

    @PostMapping("/demote/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public ResponseEntity<UserViewDto> demoteFromAdmin(@PathVariable(name = "id") String id) {
        UserModel updatedUser = userService.demoteFromAdmin(UUID.fromString(id));
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserViewDto(updatedUser));
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public ResponseEntity<Page<UserViewDto>> listAll(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<UserViewDto> users = userService.findAll(pageable).map(UserViewDto::new);
        return ResponseEntity.ok(users);
    }

    @PutMapping("/update/user/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public ResponseEntity<UserViewDto> updateUserById(@RequestBody @Valid UserUpdateDto userUpdateDto, @PathVariable String id) {
        UserModel userModel = userService.updateUser(UUID.fromString(id), userUpdateDto.toEntity());
        return ResponseEntity.ok(new UserViewDto(userModel));
    }

    private Jwt getJwtUser() {
        return  (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private UUID getPrincipalId() {
        return UUID.fromString(getJwtUser().getSubject());
    }

    private boolean isPrincipalAdmin() {
        String[] roles = getJwtUser().getClaims().get("scope").toString().split(" ");
        return Arrays.asList(roles).contains(UserRole.ROLE_ADMIN.name());
    }
}
