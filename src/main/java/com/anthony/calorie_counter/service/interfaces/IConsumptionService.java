package com.anthony.calorie_counter.service.interfaces;

import com.anthony.calorie_counter.entity.ConsumptionModel;

public interface IConsumptionService {

    ConsumptionModel updateConsumption(Long id, ConsumptionModel consumption);

    ConsumptionModel findConsumptionById(Long id);

    void deleteConsumptionById(Long id);
}
