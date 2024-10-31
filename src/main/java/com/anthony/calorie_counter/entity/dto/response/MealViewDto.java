package com.anthony.calorie_counter.entity.dto.response;

import com.anthony.calorie_counter.entity.MealModel;

import java.util.List;

public record MealViewDto(Long id, Long dailyConsumeId, String description, List<ConsumptionViewDto> consumptions) {
    public MealViewDto(MealModel meal) {
        this(
                meal.getId(),
                meal.getDailyConsume().getId(),
                meal.getDescription().getDescriptionName().name(),
                meal.getConsumptions().stream().map(ConsumptionViewDto::new).toList()
        );
    }
}
