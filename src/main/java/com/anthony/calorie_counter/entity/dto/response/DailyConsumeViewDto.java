package com.anthony.calorie_counter.entity.dto.response;

import com.anthony.calorie_counter.entity.DailyConsumeModel;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record DailyConsumeViewDto(Long Id, UUID userId, Instant date, List<MealViewDto> meals) {
    public DailyConsumeViewDto(DailyConsumeModel dailyConsume) {
        this(
                dailyConsume.getId(),
                dailyConsume.getUser().getId(),
                dailyConsume.getDate(),
                dailyConsume.getMeals().stream().map(MealViewDto::new).toList()
        );
    }
}
