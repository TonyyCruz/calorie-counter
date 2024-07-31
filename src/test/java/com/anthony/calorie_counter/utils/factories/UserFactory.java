package com.anthony.calorie_counter.utils.factories;

import com.anthony.calorie_counter.dto.request.user.UserCreateDto;
import com.anthony.calorie_counter.entity.UserModel;
import com.anthony.calorie_counter.enums.UserRole;
import com.anthony.calorie_counter.utils.SimpleFake;

public class UserFactory {
    public static UserModel createUser() {
        UserModel userModel = new UserModel();
        userModel.setId("b2283d3b-d470-40ec-9fd2-8b4eb4c5e290");
        userModel.setFullName(SimpleFake.fullName());
        userModel.setEmail(SimpleFake.email());
        userModel.setPassword(SimpleFake.password(8));
        userModel.setPhoneNumber(SimpleFake.phoneNumber());
        userModel.setRoles(UserRole.USER);
        return userModel;
    }

    public static UserModel createUser(String id) {
        UserModel userModel = new UserModel();
        userModel.setId(id);
        userModel.setFullName(SimpleFake.fullName());
        userModel.setEmail(SimpleFake.email());
        userModel.setPassword(SimpleFake.password(8));
        userModel.setPhoneNumber(SimpleFake.phoneNumber());
        userModel.setRoles(UserRole.USER);
        return userModel;
    }

    public static UserCreateDto createUserDto() {
        return new UserCreateDto(
                SimpleFake.fullName(),
                SimpleFake.email(),
                SimpleFake.password(8),
                SimpleFake.phoneNumber()
        );
    }
}
