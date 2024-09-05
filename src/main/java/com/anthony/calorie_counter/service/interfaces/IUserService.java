package com.anthony.calorie_counter.service.interfaces;

import com.anthony.calorie_counter.entity.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IUserService {
    UserModel findById(UUID id);

    UserModel create(UserModel userModel);

    UserModel updateUser(UUID id, UserModel userModel);

    void updatePassword(UUID id, String newPassword);

    void deleteById(UUID id);

    Page<UserModel> findAll(Pageable pageable);

    UserModel promoteToAdmin(UUID id);

    UserModel demoteFromAdmin(UUID id);
}
