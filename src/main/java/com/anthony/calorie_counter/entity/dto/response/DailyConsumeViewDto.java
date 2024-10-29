package com.anthony.calorie_counter.entity.dto.response;

import com.anthony.calorie_counter.entity.DailyConsumeModel;

import java.time.Instant;
import java.util.UUID;

public record DailyConsumeViewDto(Long Id, UUID userId, Instant date) {
    public DailyConsumeViewDto(DailyConsumeModel dailyConsume) {
        this(dailyConsume.getId(), dailyConsume.getUser().getId(), dailyConsume.getDate());
    }
}
