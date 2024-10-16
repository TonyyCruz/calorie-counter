package com.anthony.calorie_counter.service.interfaces;

import com.anthony.calorie_counter.entity.ConsumptionModel;

import java.util.List;

public interface IConsumptionService {

    ConsumptionModel create(ConsumptionModel consumption);

    ConsumptionModel findById(Long id);

    List<ConsumptionModel> findAllByMealId(Long mealId);

    ConsumptionModel update(Long id, ConsumptionModel consumption);

    void deleteById(Long id);
}
