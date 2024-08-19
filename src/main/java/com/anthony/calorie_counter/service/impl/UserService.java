package com.anthony.calorie_counter.service.impl;

import com.anthony.calorie_counter.entity.RoleModel;
import com.anthony.calorie_counter.entity.UserModel;
import com.anthony.calorie_counter.enums.UserRole;
import com.anthony.calorie_counter.exceptions.EntityDataNotFoundException;
import com.anthony.calorie_counter.repository.UserRepository;
import com.anthony.calorie_counter.service.IUserService;
import jakarta.persistence.EntityNotFoundException;
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
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override @Transactional(readOnly = true)
    public UserModel findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityDataNotFoundException("User not found with id: " + id));
    }

    @Override
    public UserModel create(UserModel userModel) {
        userModel.addRole(new RoleModel(UserRole.ROLE_USER));
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
        return userRepository.save(userModel);
    }

    @Override
    public UserModel updateUser(UUID id, UserModel newUserModelData) {
        try {
            UserModel userModel = userRepository.getReferenceById(id);
            userModel.setName(newUserModelData.getName());
            userModel.setEmail(newUserModelData.getEmail());
            userModel.setPhoneNumber(newUserModelData.getPhoneNumber());
            return userRepository.save(userModel);
        } catch (EntityNotFoundException e) {
            throw new EntityDataNotFoundException("User not found with id: " + id);
        }
    }

    @Transactional
    public void updatePassword(UUID id, String newPassword) {
        try {
            UserModel userModel = userRepository.getReferenceById(id);
            userModel.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(userModel);
        } catch (EntityNotFoundException e) {
            throw new EntityDataNotFoundException("User not found with id: " + id);
        }
    }

    @Override @Transactional
    public void deleteById(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new EntityDataNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public Page<UserModel> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

//    @Transactional(readOnly = true)
//    public RoleModel findRoleById(Long id) {
//        return roleRepository.findById(id)
//                .orElseThrow(() -> new EntityDataNotFoundException("Role not found with id: " + id));
//    }

    @Override @Transactional
    public UserModel promoteToAdmin(UUID id) {
        try {
            UserModel userModel = userRepository.getReferenceById(id);
            userModel.addRole(new RoleModel(UserRole.ROLE_ADMIN));
            return userRepository.save(userModel);
        } catch (EntityNotFoundException e) {
            throw new EntityDataNotFoundException("User not found with id: " + id);
        }
    }

    @Override @Transactional
    public UserModel demoteFromAdmin(UUID id) {
        try {
            UserModel userModel = userRepository.getReferenceById(id);
            userModel.removeRoleById(UserRole.ROLE_ADMIN.getRole());
            return userRepository.save(userModel);
        } catch (EntityNotFoundException e) {
            throw new EntityDataNotFoundException("User not found with id: " + id);
        }
    }

    @Override
    public UserModel loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
}
