package com.anthony.calorie_counter.service.impl;

import com.anthony.calorie_counter.entity.RoleModel;
import com.anthony.calorie_counter.entity.UserModel;
import com.anthony.calorie_counter.enums.UserRole;
import com.anthony.calorie_counter.exceptions.EntityDataNotFoundException;
import com.anthony.calorie_counter.repository.RoleRepository;
import com.anthony.calorie_counter.repository.UserRepository;
import com.anthony.calorie_counter.service.IUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserService implements IUserService, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override @Transactional(readOnly = true)
    public UserModel findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityDataNotFoundException("User not found with id: " + id));
    }

    @Override
    public UserModel create(UserModel userModel) {
        userModel.addRole(findRoleById(UserRole.ROLE_USER.getRole()));
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
        return save(userModel);
    }

    @Override
    public UserModel updateUser(UUID id, UserModel newUserModelData) {
        UserModel userModel = findById(id);
        userModel.setFullName(newUserModelData.getFullName());
        userModel.setEmail(newUserModelData.getEmail());
        userModel.setPhoneNumber(newUserModelData.getPhoneNumber());
        return save(userModel);
    }

    @Transactional
    public void updatePassword(UUID id, String newPassword) {
        userRepository.updatePasswordByUserId(id, passwordEncoder.encode(newPassword));
    }

    @Override
    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    public Page<UserModel> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public RoleModel findRoleById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new EntityDataNotFoundException("Role not found with id: " + id));
    }

    @Transactional
    private UserModel save(UserModel user) {
        return userRepository.save(user);
    }

    @Override @Transactional
    public UserModel promoteToAdmin(UUID id) {
        UserModel userModel = findById(id);
        userModel.addRole(findRoleById(UserRole.ROLE_ADMIN.getRole()));
        return userRepository.save(userModel);
    }

    @Override @Transactional
    public UserModel demoteFromAdmin(UUID id) {
        UserModel userModel = findById(id);
        userModel.removeRole(UserRole.ROLE_ADMIN.getRole());
        return userRepository.save(userModel);
    }

    @Override
    public UserModel loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with: " + username));
    }
}
