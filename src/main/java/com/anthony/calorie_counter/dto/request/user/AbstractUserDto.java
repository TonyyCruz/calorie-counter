package com.anthony.calorie_counter.dto.request.user;

import com.anthony.calorie_counter.service.validation.EmailUnique;
import com.anthony.calorie_counter.service.validation.PhoneNumberValid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public abstract class AbstractUserDto implements Serializable {
    @NotBlank(message = "The name must not be empty.")
    protected String fullName;
    @EmailUnique
    @Email(message = "Invalid Email.") @NotEmpty(message = "The email must not be empty.")
    protected String email;
    @PhoneNumberValid
    protected String phoneNumber;
}
