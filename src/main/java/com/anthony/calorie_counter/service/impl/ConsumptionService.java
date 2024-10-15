package com.anthony.calorie_counter.service.impl;

import com.anthony.calorie_counter.entity.ConsumptionModel;
import com.anthony.calorie_counter.exceptions.EntityDataNotFoundException;
import com.anthony.calorie_counter.exceptions.messages.ExceptionMessages;
import com.anthony.calorie_counter.repository.ConsumptionRepository;
import com.anthony.calorie_counter.repository.MealRepository;
import com.anthony.calorie_counter.service.interfaces.IConsumptionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsumptionService implements IConsumptionService {
    private final ConsumptionRepository consumptionRepository;
    private final MealRepository mealRepository;

    @Override
    public ConsumptionModel create(ConsumptionModel consumption) {
        return consumptionRepository.save(consumption);
    }

    @Override
    public ConsumptionModel findById(Long id) {
        return consumptionRepository.findById(id).orElseThrow(
                () -> new EntityDataNotFoundException(ExceptionMessages.DATA_NOT_FOUND_WITH_ID + id)
        );
    }

    @Override
    public List<ConsumptionModel> findAllByMealId(Long mealId) {
        return consumptionRepository.findAllByMealId(mealId);
    }

    @Override
    public ConsumptionModel update(Long id, ConsumptionModel consumption) {
        try {
            ConsumptionModel current = consumptionRepository.getReferenceById(id);
//            current.setMeal(mealRepository.getReferenceById(consumption.getMeal().getId()));
            current.setMeal(consumption.getMeal());
            current.setGrams(consumption.getGrams());
            current.setAliment(consumption.getAliment());
            return consumptionRepository.save(current);
        } catch (EntityNotFoundException e) {
            throw new EntityDataNotFoundException(ExceptionMessages.DATA_NOT_FOUND_WITH_ID + id);
        }
    }

    @Override
    public void deleteById(Long id) {
        if (!consumptionRepository.existsById(id)) {
            throw new EntityDataNotFoundException(ExceptionMessages.DATA_NOT_FOUND_WITH_ID + id);
        }
        consumptionRepository.deleteById(id);
    }
}
