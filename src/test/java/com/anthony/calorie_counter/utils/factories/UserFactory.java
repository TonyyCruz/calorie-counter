package com.anthony.calorie_counter.utils.factories;

import com.anthony.calorie_counter.dto.request.user.UserCreateDto;
import com.anthony.calorie_counter.entity.UserModel;
import com.anthony.calorie_counter.utils.SimpleFake;

import java.util.UUID;

public class UserFactory {

    public static UserCreateDto createUserDto() {
        UserCreateDto createDto = new UserCreateDto();
        createDto.setName(SimpleFake.fullName());
        createDto.setEmail(SimpleFake.email());
        createDto.setPhoneNumber(SimpleFake.phoneNumber());
        createDto.setPassword(SimpleFake.password(8));
        return createDto;
    }

    public static UserModel createUserFromDto(UserCreateDto createDto) {
        UserModel userModel = new UserModel();
        userModel.setId(UUID.randomUUID());
        userModel.setName(createDto.getName());
        userModel.setEmail(createDto.getEmail());
        userModel.setPhoneNumber(createDto.getPhoneNumber());
        userModel.setPassword(createDto.getPassword());
        return userModel;
    }

    public static UserModel createUser() {
        return createUserFromDto(createUserDto());
    }
}
