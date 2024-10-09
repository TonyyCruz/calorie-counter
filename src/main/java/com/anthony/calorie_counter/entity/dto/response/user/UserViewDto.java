package com.anthony.calorie_counter.entity.dto.response.user;

import com.anthony.calorie_counter.entity.RoleModel;
import com.anthony.calorie_counter.entity.UserModel;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;


public record UserViewDto(
        UUID id,
        String name,
        String email,
        String phoneNumber,
        Set<RoleModel> roles
) implements Serializable {

    public UserViewDto(UserModel userModel) {
        this(
                userModel.getId(),
                userModel.getName(),
                userModel.getEmail(),
                userModel.getPhoneNumber(),
                userModel.getRoles()
        );
    }
}
