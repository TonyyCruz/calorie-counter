package com.anthony.calorie_counter.service;

import com.anthony.calorie_counter.entity.RoleModel;
import com.anthony.calorie_counter.entity.UserModel;

import java.util.UUID;

public interface IUserService {
    UserModel findById(UUID id);

    UserModel save(UserModel userModel);

    UserModel updateUser(UUID id, UserModel userModel);

    void updatePassword(UUID id, String newPassword);

    void deleteById(UUID id);
}
