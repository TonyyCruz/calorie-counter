package com.anthony.calorie_counter.service.interfaces;

import com.anthony.calorie_counter.entity.ConsumptionModel;
import com.anthony.calorie_counter.entity.MealModel;

public interface IMealService {

    MealModel findMealById(Long id);

    void deleteMealById(Long id);

    MealModel addConsumption(ConsumptionModel consumption);

    MealModel deleteConsumption(ConsumptionModel consumption);
}
