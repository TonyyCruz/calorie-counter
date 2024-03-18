package com.anthony.calorie_counter.service;

import com.anthony.calorie_counter.entity.User;

public interface IUserService {
    User findById(Long id);

    User save(User user);

    User update(Long id, User user);

    void delete(Long id);
}
