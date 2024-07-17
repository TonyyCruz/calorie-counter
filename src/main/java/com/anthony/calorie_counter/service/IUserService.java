package com.anthony.calorie_counter.service;

import com.anthony.calorie_counter.entity.Role;
import com.anthony.calorie_counter.entity.User;

public interface IUserService {
    User findById(String id);

    User save(User user);

    User updateUser(String id, User user);

    void updatePassword(String id, String newPassword);

    void deleteById(String id);

    Role findRoleById(Long id);
}
