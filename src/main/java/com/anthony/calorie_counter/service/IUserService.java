package com.anthony.calorie_counter.service;

import com.anthony.calorie_counter.entity.User;

public interface IUserService {
    User findById(String id);

    User save(User user);

    User updateUser(String id, User user);

    void deleteByEmail(String id);

    User updatePassword(String email, User updateUser);

    User findByEmail(String id);

    User updateRole(String id, User updateUser);
}
