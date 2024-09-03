package com.anthony.calorie_counter.utils.factories;

import com.anthony.calorie_counter.dto.request.user.UserCreateDto;
import com.anthony.calorie_counter.dto.request.user.UserUpdateDto;
import com.anthony.calorie_counter.entity.UserModel;
import com.anthony.calorie_counter.utils.SimpleFake;

import java.util.UUID;

public class UserFactory {

    public static UserCreateDto userCreateDto() {
        UserCreateDto createDto = new UserCreateDto();
        createDto.setName(SimpleFake.fullName());
        createDto.setEmail(SimpleFake.email());
        createDto.setPhoneNumber(SimpleFake.phoneNumber());
        createDto.setPassword(SimpleFake.password(8));
        return createDto;
    }

    public static UserCreateDto cloneUserCreateDto(UserCreateDto createDto) {
        UserCreateDto newCreateDto = new UserCreateDto();
        newCreateDto.setName(createDto.getName());
        newCreateDto.setEmail(createDto.getEmail());
        newCreateDto.setPhoneNumber(createDto.getPhoneNumber());
        newCreateDto.setPassword(createDto.getPassword());
        return newCreateDto;
    }

    public static UserModel createUserEntityFromDto(UserCreateDto createDto) {
        UserModel userModel = new UserModel();
        userModel.setId(UUID.randomUUID());
        userModel.setName(createDto.getName());
        userModel.setEmail(createDto.getEmail());
        userModel.setPhoneNumber(createDto.getPhoneNumber());
        userModel.setPassword(createDto.getPassword());
        return userModel;
    }

    public static UserModel createUserEntity() {
        return createUserEntityFromDto(userCreateDto());
    }

    public static UserModel cloneUser(UserModel userModel) {
        UserModel newUserModel = new UserModel();
        newUserModel.setId(userModel.getId());
        newUserModel.setName(userModel.getName());
        newUserModel.setEmail(userModel.getEmail());
        newUserModel.setPhoneNumber(userModel.getPhoneNumber());
        newUserModel.setPassword(userModel.getPassword());
        userModel.getRoles().forEach(newUserModel::addRole);
        return newUserModel;
    }

    public static UserUpdateDto userUpdateDto() {
        UserUpdateDto updateDto = new UserUpdateDto();
        updateDto.setName(SimpleFake.fullName());
        updateDto.setEmail(SimpleFake.email());
        updateDto.setPhoneNumber(SimpleFake.phoneNumber());
        return updateDto;
    }
}
