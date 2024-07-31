package com.anthony.calorie_counter.dto.request.user;

import com.anthony.calorie_counter.entity.UserModel;
import com.anthony.calorie_counter.service.validation.PasswordValid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDto extends UserDto {
    @PasswordValid
    private String password;

    public UserModel toEntity() {
        UserModel userModel = new UserModel();
        userModel.setFullName(fullName);
        userModel.setEmail(email);
        userModel.setPassword(password);
        userModel.setPhoneNumber(phoneNumber);
        return userModel;
    }
}
