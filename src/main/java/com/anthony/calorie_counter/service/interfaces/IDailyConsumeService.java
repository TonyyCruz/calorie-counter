package com.anthony.calorie_counter.service.interfaces;

import com.anthony.calorie_counter.entity.DailyConsumeModel;
import com.anthony.calorie_counter.entity.dto.request.DailyConsumeDto;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IDailyConsumeService {

    DailyConsumeModel create(UUID userId, DailyConsumeDto dailyConsume);

    DailyConsumeModel findById(Long id);

    List<DailyConsumeModel> findAllByUserId(UUID userId);

    DailyConsumeModel findByUserIdAndDate(UUID userId, Instant date);

//    DailyConsumeModel update(Long id, DailyConsumeModel dailyConsume);

    void delete(Long id, UUID userId);
}
