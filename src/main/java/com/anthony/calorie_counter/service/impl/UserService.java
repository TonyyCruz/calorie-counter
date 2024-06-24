package com.anthony.calorie_counter.service.impl;

import com.anthony.calorie_counter.entity.User;
import com.anthony.calorie_counter.exceptions.EntityDataNotFoundException;
import com.anthony.calorie_counter.repository.UserRepository;
import com.anthony.calorie_counter.service.IUserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Override @Transactional(readOnly = true)
    public User findById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityDataNotFoundException("User %s was not found.".formatted(id)));
    }

    @Override @Transactional(readOnly = true)
    public User findByEmail(String id) {
        return userRepository.findByEmail(id)
                .orElseThrow(() -> new EntityDataNotFoundException("User %s was not found.".formatted(id)));
    }

    @Override @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override @Transactional
    public User updateUser(String id, User updateUser) {
        User user = findByEmail(id);
        user.setFullName(updateUser.getFullName());
        user.setEmail(updateUser.getEmail());
        user.setPhoneNumber(updateUser.getPhoneNumber());
        return userRepository.save(user);
    }

    @Override @Transactional
    public User updatePassword(String email, User updateUser) {
        User user = findByEmail(email);
        user.setPassword(updateUser.getPassword());
        return userRepository.save(user);
    }

    @Override @Transactional
    public User updateRole(String id, User updateUser) {
        User user = findById(id);
        user.setRole(updateUser.getRole());
        return userRepository.save(user);
    }

    @Override @Transactional
    public void deleteByEmail(String email) {
        userRepository.deleteByEmail(email);
    }
}
