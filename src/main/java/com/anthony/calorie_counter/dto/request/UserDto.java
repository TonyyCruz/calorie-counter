package com.anthony.calorie_counter.dto.request;

import com.anthony.calorie_counter.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record UserDto(
        @NotEmpty(message = "Field 'Name' must not be empty.")
        String name,
        @Email(message = "Invalid Email.") @NotEmpty(message = "Field 'Email' must not be empty.")
        String email,
        @NotEmpty(message = "Field 'Password' must not be empty.")
        String password)
{
    public User toEntity() {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }
}
