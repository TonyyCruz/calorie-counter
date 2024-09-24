package com.anthony.calorie_counter.service.validation;

import com.anthony.calorie_counter.exceptions.handler.FieldErrorMessage;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class AlimentStringFieldValidator implements ConstraintValidator<AlimentStringFieldValid, String> {
    private final Pattern REGEX = Pattern.compile("^(?=.*\\d)\\d*(?:\\.\\d\\d)?g$", Pattern.CASE_INSENSITIVE);

    @Override
    public void initialize(AlimentStringFieldValid ann){}

    @Override
    public boolean isValid(String fieldValue, ConstraintValidatorContext context) {
        List<FieldErrorMessage> list = new ArrayList<>();
        if (!REGEX.matcher(fieldValue).matches()) {
            list.add(
                    new FieldErrorMessage(
                    "alimentField",
                    "The field must have a maximum of two decimal places, no blank spaces and ends with 'g'."
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
