package com.anthony.calorie_counter.entity.dto.response;

import com.anthony.calorie_counter.entity.AlimentModel;

import java.io.Serializable;

public record AlimentViewDto(
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

    public AlimentViewDto(AlimentModel alimentModel) {
        this(
                alimentModel.getId(),
                alimentModel.getName(),
                alimentModel.getPortion(),
                alimentModel.getCalories(),
                alimentModel.getTotalFat(),
                alimentModel.getProtein(),
                alimentModel.getCarbohydrate(),
                alimentModel.getFiber(),
                alimentModel.getSugars()
        );
    }
}
