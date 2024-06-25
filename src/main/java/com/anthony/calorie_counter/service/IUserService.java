package com.anthony.calorie_counter.service;

import com.anthony.calorie_counter.entity.User;

public interface IUserService {
    User findById(String id);

    User save(User user);

    User updateUser(User user);

    void deleteById(String id);

    User findByEmail(String email);
}
