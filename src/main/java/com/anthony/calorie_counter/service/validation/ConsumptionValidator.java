package com.anthony.calorie_counter.service.validation;

import com.anthony.calorie_counter.entity.dto.request.ConsumptionDto;
import com.anthony.calorie_counter.exceptions.handler.FieldErrorMessage;
import com.anthony.calorie_counter.repository.AlimentRepository;
import com.anthony.calorie_counter.repository.DailyConsumeRepository;
import com.anthony.calorie_counter.repository.MealRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ConsumptionValidator implements ConstraintValidator<ConsumptionValid, ConsumptionDto> {
    private final MealRepository mealRepository;
    private final AlimentRepository alimentRepository;
    private final DailyConsumeRepository dailyConsumeRepository;

    @Override
    public void initialize(ConsumptionValid ann){}

    @Override
    public boolean isValid(ConsumptionDto consumptionDto, ConstraintValidatorContext context) {
        List<FieldErrorMessage> list = new ArrayList<>();
        if (!mealRepository.existsById(consumptionDto.getMealId())) {
            list.add(new FieldErrorMessage("mealId", "Meal not found with id: " + consumptionDto.getMealId()));
        }
        if (!alimentRepository.existsById(consumptionDto.getAlimentId())) {
            list.add(new FieldErrorMessage("alimentId", "Aliment not found with id: " + consumptionDto.getAlimentId()));
        }
        if (!dailyConsumeRepository.existsById(consumptionDto.getDailyConsumeId())) {
            list.add(new FieldErrorMessage("alimentId", "Daily consumption not found with id: " + consumptionDto.getDailyConsumeId()));
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
