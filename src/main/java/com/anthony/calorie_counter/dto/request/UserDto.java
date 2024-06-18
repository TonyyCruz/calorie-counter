package com.anthony.calorie_counter.dto.request;

import com.anthony.calorie_counter.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record UserDto(
        @NotBlank(message = "The name must not be empty.")
        String fullName,
        @Email(message = "Invalid Email.") @NotEmpty(message = "The email must not be empty.")
        String email,
        @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$",
                message = "The password must have at least 8 characters including at least one uppercase, one lowercase and a number.")
        String password,
        @Pattern(regexp = "^\\(0?([14689][1-9]|2([1-2]|4|[7-8])|3([1-5]|[7-8])|5(1|[3-5])|7(1|[3-5]|7|9))\\) (9\\d|7)\\d{3}[- .]\\d{4}$",
                message = "Invalid phone number.")
        String phoneNumber
){
    public User toEntity() {
        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhoneNumber(phoneNumber);
        return user;
    }
}
