package com.anthony.calorie_counter.service.impl;

import com.anthony.calorie_counter.entity.ConsumptionModel;
import com.anthony.calorie_counter.entity.dto.request.ConsumptionDto;
import com.anthony.calorie_counter.exceptions.EntityDataNotFoundException;
import com.anthony.calorie_counter.exceptions.messages.ExceptionMessages;
import com.anthony.calorie_counter.repository.AlimentRepository;
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
    private final AlimentRepository alimentRepository;

    @Override
    public ConsumptionModel create(ConsumptionDto consumptionDto) {
        ConsumptionModel consumption = new ConsumptionModel();
        return consumptionRepository.save(entityFromDto(consumption, consumptionDto));
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
    public ConsumptionModel update(Long id, ConsumptionDto consumptionDto) {
        try {
            ConsumptionModel current = consumptionRepository.getReferenceById(id);
            return consumptionRepository.save(entityFromDto(current, consumptionDto));
        } catch (EntityNotFoundException e) {
            throw new EntityDataNotFoundException(ExceptionMessages.DATA_NOT_FOUND_WITH_ID + id);
        }
    }

    @Override
    public void delete(Long id) {
        if (!consumptionRepository.existsById(id)) {
            throw new EntityDataNotFoundException(ExceptionMessages.DATA_NOT_FOUND_WITH_ID + id);
        }
        consumptionRepository.deleteById(id);
    }

    private ConsumptionModel entityFromDto(ConsumptionModel consumption, ConsumptionDto dto) {
        consumption.setMeal(mealRepository.getReferenceById(dto.getMealId()));
        consumption.setGrams(dto.getGrams());
        consumption.setAliment(alimentRepository.getReferenceById(dto.getAlimentId()));
        return consumption;
    }
}
