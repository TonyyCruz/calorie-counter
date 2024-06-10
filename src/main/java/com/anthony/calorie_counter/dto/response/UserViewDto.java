package com.anthony.calorie_counter.dto.response;

import com.anthony.calorie_counter.entity.User;

public record UserViewDto(String fullName, String email, String phoneNumber) {

    public UserViewDto(User user) {
        this(user.getFullName(), user.getEmail(), user.getPhoneNumber());
    }
}
