package com.anthony.calorie_counter.entity.dto.response;

import com.anthony.calorie_counter.entity.MealModel;

public record MealViewDto(Long id, Long dailyConsumeId, String description) {
    public MealViewDto(MealModel meal) {
        this(meal.getId(), meal.getDailyConsume().getId(), meal.getDescription().getDescriptionName().name());
    }
}
