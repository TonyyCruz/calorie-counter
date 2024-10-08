package com.anthony.calorie_counter.service.interfaces;

import com.anthony.calorie_counter.entity.ConsumptionModel;

public interface IConsumptionService {

    ConsumptionModel createConsumption(ConsumptionModel consumption);

    ConsumptionModel updateConsumption(ConsumptionModel consumption);

    ConsumptionModel findConsumptionById(Long id);

    void deleteConsumptionById(Long id);
}
