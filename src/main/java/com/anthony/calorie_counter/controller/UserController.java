package com.anthony.calorie_counter.controller;

import com.anthony.calorie_counter.dto.request.UserDto;
import com.anthony.calorie_counter.dto.response.UserView;
import com.anthony.calorie_counter.entity.User;
import com.anthony.calorie_counter.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping
    ResponseEntity<UserView> create(@RequestBody UserDto userDto) {
        User user = userDto.toEntity();
        User savedUser = userService.save(user);
        return ResponseEntity.ok().body(new UserView(savedUser));
    }

    @GetMapping("/{id}")
    ResponseEntity<UserView> findById(@PathVariable Long id) {
        User user = userService.findById(id);
        return ResponseEntity.ok().body(new UserView(user));
    }

    @PutMapping("/{id}")
    ResponseEntity<UserView> create(@PathVariable Long id, @RequestBody UserDto userDto) {
        User user = userDto.toEntity();
        User updatedUser = userService.update(id, user);
        return ResponseEntity.ok().body(new UserView(updatedUser));
    }

    @DeleteMapping("/{id}")
    ResponseEntity.HeadersBuilder<?> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent();
    }
}
