package com.anthony.calorie_counter.service.validation;

import com.anthony.calorie_counter.exceptions.handler.FieldErrorMessage;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<PasswordValid, String> {
    private final Pattern REGEX = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$");

    @Override
    public void initialize(PasswordValid ann){}

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        List<FieldErrorMessage> list = new ArrayList<>();
        if (!REGEX.matcher(password).matches()) {
            list.add(
                    new FieldErrorMessage(
                    "password",
                    "The password must have at least 8 characters including at least one uppercase, one lowercase and a number."
            ));
        }
        for(FieldErrorMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getErrorMessage())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}
