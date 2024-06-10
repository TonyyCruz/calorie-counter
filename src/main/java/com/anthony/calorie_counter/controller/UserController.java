package com.anthony.calorie_counter.controller;

import com.anthony.calorie_counter.dto.request.UserDto;
import com.anthony.calorie_counter.dto.response.UserView;
import com.anthony.calorie_counter.entity.User;
import com.anthony.calorie_counter.service.impl.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    UserService userService;

//    @PostMapping
//    ResponseEntity<UserView> create(@RequestBody @Valid UserDto userDto) {
//        User user = userDto.toEntity();
//        User savedUser = userService.save(user);
//        return ResponseEntity.status(HttpStatus.CREATED).body(new UserView(savedUser));
//    }

    @GetMapping("/{id}")
    ResponseEntity<UserView> findById(@PathVariable String id) {
        User user = userService.findById(id);
        return ResponseEntity.ok(new UserView(user));
    }

    @PutMapping("/{id}")
    ResponseEntity<UserView> update(@PathVariable String id, @RequestBody @Valid UserDto userDto) {
        User user = userDto.toEntity();
        User updatedUser = userService.update(id, user);
        return ResponseEntity.ok(new UserView(updatedUser));
    }

//    @DeleteMapping("/{id}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    ResponseEntity<?> delete(@PathVariable String id) {
//        userService.delete(id);
//        return ResponseEntity.noContent().build();
//    }
}
