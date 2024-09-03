package com.anthony.calorie_counter.dto.response.food;

import com.anthony.calorie_counter.entity.FoodModel;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

public record FoodViewDto(
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

    public FoodViewDto(FoodModel foodModel) {
        this(
                foodModel.getId(),
                foodModel.getName(),
                foodModel.getPortion(),
                foodModel.getCalories(),
                foodModel.getTotalFat(),
                foodModel.getProtein(),
                foodModel.getCarbohydrate(),
                foodModel.getFiber(),
                foodModel.getSugars()
        );
    }
}
