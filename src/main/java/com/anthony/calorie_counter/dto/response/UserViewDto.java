package com.anthony.calorie_counter.dto.response;

import com.anthony.calorie_counter.entity.User;
import com.anthony.calorie_counter.enums.UserRole;

public record UserViewDto(String fullName, String email, String phoneNumber, UserRole role) {

    public UserViewDto(User user) {
        this(user.getFullName(), user.getEmail(), user.getPhoneNumber(), user.getRole());
    }
}
