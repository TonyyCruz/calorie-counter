package com.anthony.calorie_counter.service.impl;

import com.anthony.calorie_counter.entity.User;
import com.anthony.calorie_counter.repository.UserRepository;
import com.anthony.calorie_counter.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User was not found."));
    }

    private boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public User save(User user) {
        if (emailExists(user.getEmail())) {
            throw new IllegalArgumentException("This email is already in use.");
        }
        return userRepository.save(user);
    }

    @Override
    public User update(Long id, User user) {
        findById(id);
        user.setId(id);
        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        findById(id);
        userRepository.deleteById(id);
    }


}
