package com.anthony.calorie_counter.entity.dto.request.user;

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
public class UserCreateDto extends AbstractUserDto {
    @PasswordValid
    private String password;

    public UserCreateDto(String name, String email, String phone, String password) {
        super(name, email, phone);
        this.password = password;
    }

    public UserModel toEntity() {
        UserModel userModel = new UserModel();
        userModel.setName(name);
        userModel.setEmail(email);
        userModel.setPassword(password);
        userModel.setPhoneNumber(phoneNumber);
        return userModel;
    }
}
