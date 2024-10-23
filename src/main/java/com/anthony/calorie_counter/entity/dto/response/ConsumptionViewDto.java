package com.anthony.calorie_counter.entity.dto.response;

import com.anthony.calorie_counter.entity.ConsumptionModel;

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
