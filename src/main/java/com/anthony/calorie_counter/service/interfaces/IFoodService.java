package com.anthony.calorie_counter.service.interfaces;

import com.anthony.calorie_counter.entity.MealModel;

public interface IFoodService {
    MealModel create(MealModel food);
    MealModel update(Long id, MealModel food);
    void delete(Long id);
}
