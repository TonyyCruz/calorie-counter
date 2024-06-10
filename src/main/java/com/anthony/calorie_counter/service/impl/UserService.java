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

    @Override @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override @Transactional
    public User update(String id, User updateUser) {
        try {
        User user = userRepository.getReferenceById(id);
        user.setFullName(updateUser.getFullName());
        user.setEmail(updateUser.getEmail());
        user.setPassword(updateUser.getPassword());
        user.setPhoneNumber(updateUser.getPhoneNumber());
        return userRepository.save(user);
        } catch (EntityNotFoundException e) {
            throw new EntityDataNotFoundException("User %s was not found.".formatted(id));
        }
    }

    @Override @Transactional
    public void delete(String id) {
        if (!userRepository.existsById(id)) throw new EntityDataNotFoundException("User %s was not found.".formatted(id));
        userRepository.deleteById(id);
    }
}
