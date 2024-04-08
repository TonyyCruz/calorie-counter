package com.anthony.calorie_counter.utils.factories;

import com.anthony.calorie_counter.dto.request.UserDto;
import com.anthony.calorie_counter.entity.User;
import com.anthony.calorie_counter.utils.SimpleFake;

public class UserFactory {
    public static User createUser() {
        User user = new User();
        user.setId(SimpleFake.randomLong());
        user.setFullName(SimpleFake.fullName());
        user.setEmail(SimpleFake.email());
        user.setPassword(SimpleFake.password());
        return user;
    }

    public static User createUser(Long id) {
        User user = new User();
        user.setId(id);
        user.setFullName(SimpleFake.fullName());
        user.setEmail(SimpleFake.email());
        user.setPassword(SimpleFake.password());
        return user;
    }

    public static UserDto createUserDto() {
        return new UserDto(SimpleFake.fullName(), SimpleFake.email(), SimpleFake.password());
    }
}
