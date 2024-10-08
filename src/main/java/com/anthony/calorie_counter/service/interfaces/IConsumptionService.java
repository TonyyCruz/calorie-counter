package com.anthony.calorie_counter.service.interfaces;

import com.anthony.calorie_counter.entity.MealModel;

public interface IMealService {

    MealModel findById(Long id);

    void deleteConsumptionById(Long id);

    void deleteMealById(Long id);
}
