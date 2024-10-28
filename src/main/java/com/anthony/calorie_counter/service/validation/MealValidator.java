package com.anthony.calorie_counter.service.validation;

import com.anthony.calorie_counter.entity.dto.request.MealDto;
import com.anthony.calorie_counter.exceptions.handler.FieldErrorMessage;
import com.anthony.calorie_counter.repository.DailyConsumeRepository;
import com.anthony.calorie_counter.repository.DescriptionRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class MealValidator implements ConstraintValidator<MealValid, MealDto> {
    private final DescriptionRepository descriptionRepository;
    private final DailyConsumeRepository dailyConsumeRepository;

    @Override
    public void initialize(MealValid ann){}

    @Override
    public boolean isValid(MealDto mealDto, ConstraintValidatorContext context) {
        List<FieldErrorMessage> list = new ArrayList<>();
        if (!descriptionRepository.existsById(mealDto.getDescriptionId())) {
            list.add(new FieldErrorMessage("descriptionId", "Description not found with id: " + mealDto.getDescriptionId()));
        }
        if (!dailyConsumeRepository.existsById(mealDto.getDailyConsumeId())) {
            list.add(new FieldErrorMessage("dailyConsumeId", "Daily consume not found with id: " + mealDto.getDailyConsumeId()));
        }
        for (FieldErrorMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getErrorMessage())
                    .addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}
