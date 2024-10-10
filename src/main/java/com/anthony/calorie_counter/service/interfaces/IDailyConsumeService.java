package com.anthony.calorie_counter.service.interfaces;


import com.anthony.calorie_counter.entity.DailyConsumeModel;

import java.util.List;
import java.util.UUID;

public interface IDailyConsumeService {

    DailyConsumeModel create(DailyConsumeModel dailyConsume);

    DailyConsumeModel findById(Long id);

    List<DailyConsumeModel> findAllByUserId(UUID userId);

    DailyConsumeModel updateById(Long id, DailyConsumeModel dailyConsume);

    void deleteById(Long id);
}
