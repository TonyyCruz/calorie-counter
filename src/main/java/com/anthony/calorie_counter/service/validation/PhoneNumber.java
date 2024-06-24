package com.anthony.calorie_counter.service.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PhoneNumberValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)

public @interface PhoneNumber {
    String message() default "Validation error.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
