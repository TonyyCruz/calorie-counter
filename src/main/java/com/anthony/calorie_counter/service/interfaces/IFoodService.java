package com.anthony.calorie_counter.service.interfaces;

import com.anthony.calorie_counter.entity.FoodModel;

public interface IFoodService {
    FoodModel create(FoodModel food);
    FoodModel update(Long id, FoodModel food);
    void delete(Long id);
}
