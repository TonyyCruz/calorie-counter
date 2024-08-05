package com.anthony.calorie_counter.service.impl;

import com.anthony.calorie_counter.entity.RoleModel;
import com.anthony.calorie_counter.entity.UserModel;
import com.anthony.calorie_counter.enums.UserRole;
import com.anthony.calorie_counter.exceptions.EntityDataNotFoundException;
import com.anthony.calorie_counter.exceptions.InvalidCredentialsException;
import com.anthony.calorie_counter.repository.RoleRepository;
import com.anthony.calorie_counter.repository.UserRepository;
import com.anthony.calorie_counter.service.IRoleService;
import com.anthony.calorie_counter.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserService implements IUserService, IRoleService, UserDetailsService {
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
    public UserModel create(UserRole role, UserModel userModel) {
        RoleModel roleModel = findRoleById((long) role.getRole());
        userModel.addRole(roleModel);
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
        return save(userModel);
    }

    @Override
    public UserModel updateUser(String username, UserModel newUserModelData) {
        UserModel userModel = loadUserByUsername(username);
        userModel.setFullName(newUserModelData.getFullName());
        userModel.setEmail(newUserModelData.getEmail());
        userModel.setPhoneNumber(newUserModelData.getPhoneNumber());
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

    @Override @Transactional
    public void delete(String username, UUID id) {
        UserModel user = loadUserByUsername(username);
        boolean havePermission = user.getId().equals(id) || user.isAdmin();
        if (!havePermission) { throw new InvalidCredentialsException("Old password is incorrect."); }
        userRepository.deleteById(id);
    }

    @Override
    public Page<UserModel> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override @Transactional(readOnly = true)
    public RoleModel findRoleById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new EntityDataNotFoundException("Role not found with id: " + id));
    }

    @Override @Transactional
    public UserModel save(UserModel user) {
        return userRepository.save(user);
    }



    @Override
    public UserModel loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with: " + username));
    }
}
