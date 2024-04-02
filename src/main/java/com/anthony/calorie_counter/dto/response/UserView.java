package com.anthony.calorie_counter.dto.response;

import com.anthony.calorie_counter.entity.User;

public record UserView(String name, String email) {

    public UserView(User user) {
        this(user.getName(), user.getEmail());
    }
}
