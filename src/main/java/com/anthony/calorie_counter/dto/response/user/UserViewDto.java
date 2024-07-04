package com.anthony.calorie_counter.dto.response.user;

import com.anthony.calorie_counter.entity.Role;
import com.anthony.calorie_counter.entity.User;

import java.io.Serializable;
import java.util.Set;


public record UserViewDto(
        String id,
        String fullName,
        String email,
        String phoneNumber,
        Set<Role> roles
) implements Serializable {


    public UserViewDto(User user) {
        this(user.getId(), user.getFullName(), user.getEmail(), user.getPhoneNumber(), user.getRoles());
    }
}
