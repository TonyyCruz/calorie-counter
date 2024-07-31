package com.anthony.calorie_counter.service.impl;

import com.anthony.calorie_counter.entity.RoleModel;
import com.anthony.calorie_counter.entity.UserModel;
import com.anthony.calorie_counter.enums.UserRole;
import com.anthony.calorie_counter.exceptions.EntityDataNotFoundException;
import com.anthony.calorie_counter.repository.RoleRepository;
import com.anthony.calorie_counter.repository.UserRepository;
import com.anthony.calorie_counter.service.IRoleService;
import com.anthony.calorie_counter.service.IUserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserService implements IUserService, IRoleService, UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override @Transactional(readOnly = true)
    public UserModel findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityDataNotFoundException("User not found with id: " + id));
    }

    @Override @Transactional
    public UserModel save(UserModel userModel) {
        RoleModel roleModel = findRoleById((long) UserRole.ROLE_USER.getRole());
        userModel.addRole(roleModel);
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
        return userRepository.save(userModel);
    }

    @Override @Transactional
    public UserModel updateUser(UUID id, UserModel newUserModelData) {
        try {
            UserModel userModel = userRepository.getReferenceById(id);
            userModel.setFullName(newUserModelData.getFullName());
            userModel.setEmail(newUserModelData.getEmail());
            userModel.setPhoneNumber(newUserModelData.getPhoneNumber());
            return userRepository.save(userModel);
        } catch (EntityNotFoundException e) {
            throw new EntityDataNotFoundException("User not found with id: " + id);
        }
    }

    @Transactional
    public void updatePassword(UUID id, String newPassword) {
        userRepository.updatePasswordByUserId(id, passwordEncoder.encode(newPassword));
    }

    @Override @Transactional
    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }

    @Override @Transactional(readOnly = true)
    public RoleModel findRoleById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new EntityDataNotFoundException("Role not found with id: " + id));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with: " + username));
    }
}
