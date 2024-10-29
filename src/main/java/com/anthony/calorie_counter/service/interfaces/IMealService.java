package com.anthony.calorie_counter.service.interfaces;

import com.anthony.calorie_counter.entity.MealModel;
import com.anthony.calorie_counter.entity.dto.request.MealDto;

import java.util.List;

public interface IMealService {

    MealModel create(MealDto mealDto);

    MealModel findById(Long id);

    List<MealModel> findAllByDailyConsumeId(Long dailyConsumeId);

    MealModel update(Long id, MealDto mealDto);

    void delete(Long id);
}
