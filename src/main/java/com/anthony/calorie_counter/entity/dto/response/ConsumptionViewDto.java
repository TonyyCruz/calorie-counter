package com.anthony.calorie_counter.entity.dto.response;

import com.anthony.calorie_counter.entity.AlimentModel;
import com.anthony.calorie_counter.entity.ConsumptionModel;
import com.anthony.calorie_counter.entity.MealModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

public record ConsumptionViewDto(
        Long id,
        Integer grams,
        AlimentViewDto aliment
) implements Serializable {

    public ConsumptionViewDto(ConsumptionModel consumption) {
        this(consumption.getId(), consumption.getGrams(), new AlimentViewDto(consumption.getAliment()));
    }
}
