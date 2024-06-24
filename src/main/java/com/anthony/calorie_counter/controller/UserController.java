package com.anthony.calorie_counter.controller;

import com.anthony.calorie_counter.dto.request.UserCreateDto;
import com.anthony.calorie_counter.dto.response.UserViewDto;
import com.anthony.calorie_counter.entity.User;
import com.anthony.calorie_counter.service.impl.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    // VALIDAR SE O ID É REALMNTE DO USUARIO
    @PutMapping("/{id}")
    ResponseEntity<UserViewDto> update(@PathVariable String id, @RequestBody @Valid UserCreateDto userCreateDto) {
        User user = userCreateDto.toEntity();
        User updatedUser = userService.update(id, user);
        return ResponseEntity.ok(new UserViewDto(updatedUser));
    }

    // VALIDAR SE O ID É REALMNTE DO USUARIO
//    @DeleteMapping("/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    ResponseEntity<?> delete(@PathVariable String id) {
//        userService.delete(id);
//        return ResponseEntity.noContent().build();
//    }
}
