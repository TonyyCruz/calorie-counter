package com.anthony.calorie_counter.service.impl;

import com.anthony.calorie_counter.entity.User;
import com.anthony.calorie_counter.exceptions.NotFoundException;
import com.anthony.calorie_counter.repository.UserRepository;
import com.anthony.calorie_counter.service.IUserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements IUserService {
    @Autowired
    UserRepository userRepository;

    @Override @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User %d was not found.".formatted(id)));
    }

    @Override @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override @Transactional
    public User update(Long id, User updateUser) {
        try {
        User user = userRepository.getReferenceById(id);
        user.setFullName(updateUser.getFullName());
        user.setEmail(updateUser.getEmail());
        user.setPassword(updateUser.getPassword());
        return userRepository.save(user);
        } catch (EntityNotFoundException e) {
            throw new NotFoundException("User %d was not found.".formatted(id));
        }
    }

    @Override
    public void delete(Long id) {
        if (!userRepository.existsById(id)) throw new NotFoundException("User %d was not found.".formatted(id));
        userRepository.deleteById(id);
    }
}
