package com.anthony.calorie_counter.dto.response;

import com.anthony.calorie_counter.entity.User;

public record UserView(String fullName, String email, String phoneNumber) {

    public UserView(User user) {
        this(user.getFullName(), user.getEmail(), user.getPhoneNumber());
    }
}
