package com.anthony.calorie_counter.exceptions.handler;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationError extends ExceptionDetails {
    private final List<FieldErrorMessage> fieldsError = new ArrayList<>();

    public void addFieldError(String field, String error) {
        fieldsError.add(new FieldErrorMessage(field, error));
    }
}
