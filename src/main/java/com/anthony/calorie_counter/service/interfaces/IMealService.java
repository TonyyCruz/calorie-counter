package com.anthony.calorie_counter.service.interfaces;

import com.anthony.calorie_counter.entity.MealModel;

import java.util.List;

public interface IMealService {

    MealModel create(MealModel meal);

    MealModel findById(Long id);

    List<MealModel> findAllByDailyConsumeId(Long dailyConsumeId);

    MealModel updateById(Long id, MealModel meal);

    void deleteById(Long id);
}
