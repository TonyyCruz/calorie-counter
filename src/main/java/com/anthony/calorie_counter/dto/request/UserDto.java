package com.anthony.calorie_counter.dto.request;

import com.anthony.calorie_counter.entity.User;

public record UserDto(String name, String email, String password) {
    public User toEntity() {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }
}
