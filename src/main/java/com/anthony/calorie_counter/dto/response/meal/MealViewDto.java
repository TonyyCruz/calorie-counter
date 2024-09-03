package com.anthony.calorie_counter.dto.response.meal;

import com.anthony.calorie_counter.entity.MealModel;

import java.io.Serializable;

public record MealViewDto(
        Long id,
        String name,
        String portion,
        Integer calories,
        String totalFat,
        String protein,
        String carbohydrate,
        String fiber,
        String sugars
) implements Serializable {

    public MealViewDto(MealModel mealModel) {
        this(
                mealModel.getId(),
                mealModel.getName(),
                mealModel.getPortion(),
                mealModel.getCalories(),
                mealModel.getTotalFat(),
                mealModel.getProtein(),
                mealModel.getCarbohydrate(),
                mealModel.getFiber(),
                mealModel.getSugars()
        );
    }
}
