package com.anthony.calorie_counter.dto.request;

import com.anthony.calorie_counter.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record UserDto(
        @NotEmpty(message = "The name must not be empty.")
        String fullName,
        @Email(message = "Invalid Email.") @NotEmpty(message = "The email must not be empty.")
        String email,
        @NotEmpty(message = "The password must not be empty.")
        @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$",
                message = "The password must have at least 8 characters including at least one uppercase, one lowercase and a number.")
        String password
){
    public User toEntity() {
        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }
}
