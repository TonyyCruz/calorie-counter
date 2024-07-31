package com.anthony.calorie_counter.service.impl;

import com.anthony.calorie_counter.entity.RoleModel;
import com.anthony.calorie_counter.entity.UserModel;
import com.anthony.calorie_counter.enums.UserRole;
import com.anthony.calorie_counter.exceptions.EntityDataNotFoundException;
import com.anthony.calorie_counter.exceptions.UnauthorizedException;
import com.anthony.calorie_counter.repository.RoleRepository;
import com.anthony.calorie_counter.repository.UserRepository;
import com.anthony.calorie_counter.service.IRoleService;
import com.anthony.calorie_counter.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public UserModel create(UserModel userModel) {
        RoleModel roleModel = findRoleById((long) UserRole.ROLE_USER.getRole());
        userModel.addRole(roleModel);
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
        return userRepository.save(userModel);
    }

    @Override @Transactional
    public UserModel updateUser(String username, UserModel newUserModelData) {
        UserModel userModel = loadUserByUsername(username);
        userModel.setFullName(newUserModelData.getFullName());
        userModel.setEmail(newUserModelData.getEmail());
        userModel.setPhoneNumber(newUserModelData.getPhoneNumber());
        return userRepository.save(userModel);
    }

    @Override @Transactional
    public UserModel updateUser(UUID id, UserModel newUserModelData) {
        UserModel userModel = findById(id);
        userModel.setFullName(newUserModelData.getFullName());
        userModel.setEmail(newUserModelData.getEmail());
        userModel.setPhoneNumber(newUserModelData.getPhoneNumber());
        return userRepository.save(userModel);
    }

    @Transactional
    public void updatePassword(UUID id, String newPassword) {
        userRepository.updatePasswordByUserId(id, passwordEncoder.encode(newPassword));
    }

    @Transactional
    public void deleteByUsernameAndId(String username, UUID id) {
        UserModel user = loadUserByUsername(username);
        boolean havePermission = user.getId().equals(id) || user.isAdmin();
        if (!havePermission) { throw new UnauthorizedException("Old password is incorrect."); }
        deleteById(id);
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
    public UserModel loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with: " + username));
    }
}
