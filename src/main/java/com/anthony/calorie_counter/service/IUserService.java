package com.anthony.calorie_counter.service;

import com.anthony.calorie_counter.entity.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IUserService {
    UserModel findById(UUID id);

    UserModel create(UserModel userModel);

    UserModel updateUser(String username, UserModel userModel);

    UserModel updateUser(UUID id, UserModel userModel);

    void updatePassword(UUID id, String newPassword);

    void delete(String username, UUID id);

    Page<UserModel> findAll(Pageable pageable);
}
