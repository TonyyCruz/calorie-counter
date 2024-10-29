package com.anthony.calorie_counter.service.interfaces;


import com.anthony.calorie_counter.entity.DailyConsumeModel;
import com.anthony.calorie_counter.entity.dto.request.DailyConsumeDto;

import java.util.List;
import java.util.UUID;

public interface IDailyConsumeService {

    DailyConsumeModel create(UUID userId, DailyConsumeDto dailyConsume);

    DailyConsumeModel findById(Long id);

    List<DailyConsumeModel> findAllByUserId(UUID userId);

    DailyConsumeModel update(Long id, DailyConsumeModel dailyConsume);

    void delete(Long id);
}
