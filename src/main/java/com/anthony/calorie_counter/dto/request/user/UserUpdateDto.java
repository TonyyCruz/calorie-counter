package com.anthony.calorie_counter.dto.request.user;

import com.anthony.calorie_counter.entity.UserModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserUpdateDto extends AbstractUserDto {
    public UserModel toEntity() {
        UserModel userModel = new UserModel();
        userModel.setFullName(fullName);
        userModel.setEmail(email);
        userModel.setPhoneNumber(phoneNumber);
        return userModel;
    }
}
