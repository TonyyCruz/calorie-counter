package com.anthony.calorie_counter.service.validation;

import com.anthony.calorie_counter.exceptions.handler.FieldErrorMessage;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumberValid, String> {
    private final Pattern REGEX = Pattern.compile("^\\(0?([14689][1-9]|2([1-2]|4|[7-8])|3([1-5]|[7-8])|5(1|[3-5])|7(1|[3-5]|7|9))\\) (9\\d|7)\\d{3}[- .]\\d{4}$");

    @Override
    public void initialize(PhoneNumberValid ann){}

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        List<FieldErrorMessage> list = new ArrayList<>();
        // Testes de validaçãoa baixo. Insere os erros da validação de email a lista com minha classe "FieldErrorMessage".
        if (!REGEX.matcher(phoneNumber).matches()) {
            list.add(new FieldErrorMessage("phoneNumber", "Invalid phone number, please enter valid data in the format: '(11) 99999-9999'."));
        }
        for(FieldErrorMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getErrorMessage())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}
