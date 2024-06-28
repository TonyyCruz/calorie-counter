package com.anthony.calorie_counter.dto.request;

import com.anthony.calorie_counter.entity.User;
import com.anthony.calorie_counter.service.validation.PasswordValid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto extends UserDto {
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
}
