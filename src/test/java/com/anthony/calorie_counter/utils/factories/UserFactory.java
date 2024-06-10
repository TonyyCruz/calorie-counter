package com.anthony.calorie_counter.utils.factories;

import com.anthony.calorie_counter.dto.request.UserDto;
import com.anthony.calorie_counter.entity.User;
import com.anthony.calorie_counter.enums.UserRole;
import com.anthony.calorie_counter.utils.SimpleFake;

public class UserFactory {
    public static User createUser() {
        User user = new User();
        user.setId("b2283d3b-d470-40ec-9fd2-8b4eb4c5e290");
        user.setFullName(SimpleFake.fullName());
        user.setEmail(SimpleFake.email());
        user.setPassword(SimpleFake.password(8));
        user.setPhoneNumber(SimpleFake.phoneNumber());
        user.setRole(UserRole.USER);
        return user;
    }

    public static User createUser(String id) {
        User user = new User();
        user.setId(id);
        user.setFullName(SimpleFake.fullName());
        user.setEmail(SimpleFake.email());
        user.setPassword(SimpleFake.password(8));
        user.setPhoneNumber(SimpleFake.phoneNumber());
        user.setRole(UserRole.USER);
        return user;
    }

    public static UserDto createUserDto() {
        return new UserDto(
                SimpleFake.fullName(),
                SimpleFake.email(),
                SimpleFake.password(8),
                SimpleFake.phoneNumber()
        );
    }
}
