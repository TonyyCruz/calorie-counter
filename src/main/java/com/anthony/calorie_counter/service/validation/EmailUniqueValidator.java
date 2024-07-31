package com.anthony.calorie_counter.service.validation;

import com.anthony.calorie_counter.entity.User;
import com.anthony.calorie_counter.exceptions.handler.FieldErrorMessage;
import com.anthony.calorie_counter.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmailUniqueValidator implements ConstraintValidator<EmailUnique, String> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void initialize(EmailUnique ann){}

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        List<FieldErrorMessage> list = new ArrayList<>();
        if (!isUniqueEmail(email)) {
            list.add(new FieldErrorMessage("email", "This email is already in use."));
        }
        for(FieldErrorMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getErrorMessage())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }

    private boolean isUniqueEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) return true;
        if (SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) return false;
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getId().equals(user.get().getId());
    }
}
