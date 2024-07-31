package com.anthony.calorie_counter.dto.response.user;

import com.anthony.calorie_counter.entity.RoleModel;
import com.anthony.calorie_counter.entity.UserModel;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;


public record UserViewDto(
        UUID id,
        String fullName,
        String email,
        String phoneNumber,
        Set<RoleModel> roleModels
) implements Serializable {


    public UserViewDto(UserModel userModel) {
        this(userModel.getId(), userModel.getFullName(), userModel.getEmail(), userModel.getPhoneNumber(), userModel.getRoles());
    }
}
