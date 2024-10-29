package com.anthony.calorie_counter.service.interfaces;

import com.anthony.calorie_counter.entity.ConsumptionModel;
import com.anthony.calorie_counter.entity.dto.request.ConsumptionDto;

import java.util.List;

public interface IConsumptionService {

    ConsumptionModel create(ConsumptionDto consumption);

    ConsumptionModel findById(Long id);

    List<ConsumptionModel> findAllByMealId(Long mealId);

    ConsumptionModel update(Long id, ConsumptionDto consumptionDto);

    void delete(Long id);
}
