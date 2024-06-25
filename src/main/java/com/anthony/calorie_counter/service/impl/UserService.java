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
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityDataNotFoundException("User %s was not found.".formatted(email)));
    }

    @Override @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override @Transactional
    public User updateUser(User updateUser) {
//        User user = findByEmail(updateUser.getEmail());
//        user.setFullName(updateUser.getFullName());
//        user.setEmail(updateUser.getEmail());
//        user.setPhoneNumber(updateUser.getPhoneNumber());
//        return userRepository.save(user);
        try {
            User user = userRepository.getReferenceById(updateUser.getId());
            user.setFullName(updateUser.getFullName());
            user.setEmail(updateUser.getEmail());
            user.setPassword(updateUser.getPassword());
            user.setPhoneNumber(updateUser.getPhoneNumber());
            user.setRole(updateUser.getRole());
            return userRepository.save(user);
        } catch (EntityNotFoundException e) {
            throw new EntityDataNotFoundException("User %s was not found.".formatted(updateUser.getId()));
        }
    }

    @Override @Transactional
    public void deleteById(String id) {
        userRepository.deleteById(id);
    }
}
