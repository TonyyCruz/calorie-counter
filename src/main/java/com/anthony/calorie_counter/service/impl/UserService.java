package com.anthony.calorie_counter.service.impl;

import com.anthony.calorie_counter.entity.Role;
import com.anthony.calorie_counter.entity.User;
import com.anthony.calorie_counter.enums.UserRole;
import com.anthony.calorie_counter.exceptions.EntityDataNotFoundException;
import com.anthony.calorie_counter.repository.RoleRepository;
import com.anthony.calorie_counter.repository.UserRepository;
import com.anthony.calorie_counter.service.IUserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override @Transactional(readOnly = true)
    public User findById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityDataNotFoundException("User %s was not found.".formatted(id)));
    }

    @Override @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityDataNotFoundException("User '%s' was not found.".formatted(email)));
    }

    @Override @Transactional
    public User save(User user) {
        Role role = findRoleById((long) UserRole.ROLE_USER.getRole());
        user.addRole(role);
        return userRepository.save(user);
    }

    @Override @Transactional
    public User updateUser(String id, User updateUser) {
        try {
            User user = userRepository.getReferenceById(id);
            user.setFullName(updateUser.getFullName());
            user.setEmail(updateUser.getEmail());
            user.setPassword(updateUser.getPassword());
            user.setPhoneNumber(updateUser.getPhoneNumber());
            user.addRoles(updateUser.getRoles());
            return userRepository.save(user);
        } catch (EntityNotFoundException e) {
            throw new EntityDataNotFoundException("User %s was not found.".formatted(updateUser.getId()));
        }
    }

    @Override @Transactional
    public void deleteById(String id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public void updatePassword(String id, String newPassword) {
        userRepository.updatePasswordById(id, newPassword);
    }

    private Role findRoleById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new EntityDataNotFoundException("Role wit id: %d was not fount.".formatted(id)));
    }
}
