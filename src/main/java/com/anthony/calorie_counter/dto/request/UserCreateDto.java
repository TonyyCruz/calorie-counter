package com.anthony.calorie_counter.dto.request;

import com.anthony.calorie_counter.entity.User;
import com.anthony.calorie_counter.service.validation.PasswordValid;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserCreateDto extends UserUpdateDto{
    @PasswordValid
    private String password;

    public User toEntity() {
        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhoneNumber(phoneNumber);
        return user;
    }

    @Builder
    public UserCreateDto(String fullName, String email, String phoneNumber, String password) {
        super(fullName, email, phoneNumber);
        this.password = password;
    }
}
