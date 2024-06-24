package com.anthony.calorie_counter.dto.request;

import com.anthony.calorie_counter.entity.User;
import com.anthony.calorie_counter.service.validation.EmailUnique;
import com.anthony.calorie_counter.service.validation.PhoneNumberValid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto {
    @NotBlank(message = "The name must not be empty.")
    protected String fullName;
    @EmailUnique
    @Email(message = "Invalid Email.") @NotEmpty(message = "The email must not be empty.")
    protected String email;
    @PhoneNumberValid
    protected String phoneNumber;

    public User toEntity() {
        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhoneNumber(phoneNumber);
        return user;
    }
}


