package com.anthony.calorie_counter.service.validation;

import com.anthony.calorie_counter.dto.request.UserDto;
import com.anthony.calorie_counter.exceptions.handler.FieldErrorMessage;
import com.anthony.calorie_counter.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserDto> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(UserInsertValid ann){}

    @Override
    public boolean isValid(UserDto userDto, ConstraintValidatorContext context) {
        List<FieldErrorMessage> list = new ArrayList<>();
        // Testes de validaçãoa baixo. Insere os erros da validação de email a lista com minha classe "FieldErrorMessage".
        if (userRepository.findByEmail(userDto.email()).isPresent()) {
            list.add(new FieldErrorMessage("email", "The email is already in use."));
        }
        for(FieldErrorMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getErrorMessage())
                    .addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}
