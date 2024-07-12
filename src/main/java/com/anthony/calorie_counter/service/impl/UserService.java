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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements IUserService, UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override @Transactional(readOnly = true)
    public User findById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityDataNotFoundException("User with id %s was not found.".formatted(id)));
    }

    @Override @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityDataNotFoundException("User with email '%s' was not found.".formatted(email)));
    }

    @Override @Transactional
    public User save(User user) {
        Role role = findRoleById((long) UserRole.ROLE_USER.getRole());
        user.addRole(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override @Transactional
    public User updateUser(String id, User newUserData) {
        try {
            User user = userRepository.getReferenceById(id);
            user.setFullName(newUserData.getFullName());
            user.setEmail(newUserData.getEmail());
            user.setPhoneNumber(newUserData.getPhoneNumber());
            return userRepository.save(user);
        } catch (EntityNotFoundException e) {
            throw new EntityDataNotFoundException("User with id '%s' was not found.".formatted(id));
        }
    }

    @Transactional
    public void updatePassword(String id, String newPassword) {
        userRepository.updatePasswordById(id, passwordEncoder.encode(newPassword));
    }

    @Override @Transactional
    public void deleteById(String id) {
        userRepository.deleteById(id);
    }

    @Override @Transactional(readOnly = true)
    public Role findRoleById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new EntityDataNotFoundException("Role with id %d was not fount.".formatted(id)));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByEmail(username);
    }
}
