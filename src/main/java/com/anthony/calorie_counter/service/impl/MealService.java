package com.anthony.calorie_counter.service.impl;

import com.anthony.calorie_counter.entity.MealModel;
import com.anthony.calorie_counter.entity.dto.request.MealDto;
import com.anthony.calorie_counter.exceptions.EntityDataNotFoundException;
import com.anthony.calorie_counter.exceptions.messages.ExceptionMessages;
import com.anthony.calorie_counter.repository.DailyConsumeRepository;
import com.anthony.calorie_counter.repository.DescriptionRepository;
import com.anthony.calorie_counter.repository.MealRepository;
import com.anthony.calorie_counter.service.interfaces.IMealService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MealService implements IMealService {
    private final MealRepository mealRepository;
    private final DescriptionRepository descriptionRepository;
    private final DailyConsumeRepository dailyConsumeRepository;

    @Override
    public MealModel create(MealDto mealDto) {
        MealModel meal = new MealModel();
        return mealRepository.save(entityFromDto(meal, mealDto));
    }

    @Override
    public MealModel findById(Long id) {
        return mealRepository.findById(id).orElseThrow(
                () -> new EntityDataNotFoundException(ExceptionMessages.DATA_NOT_FOUND_WITH_ID + id)
        );
    }

    @Override
    public List<MealModel> findAllByDailyConsumeId(Long dailyConsumeId) {
        return mealRepository.findAllByDailyConsumeId(dailyConsumeId);
    }

    @Override
    public MealModel update(Long id, MealDto mealDto) {
        try {
            MealModel current = mealRepository.getReferenceById(id);
            return mealRepository.save(entityFromDto(current, mealDto));
        } catch (EntityNotFoundException e) {
            throw new EntityDataNotFoundException(ExceptionMessages.DATA_NOT_FOUND_WITH_ID + id);
        }
    }

    @Override
    public void delete(Long id) {
        if (!mealRepository.existsById(id)) {
            throw new EntityDataNotFoundException(ExceptionMessages.DATA_NOT_FOUND_WITH_ID+ id);
        }
        mealRepository.deleteById(id);
    }

    private MealModel entityFromDto(MealModel meal, MealDto dto) {
        meal.setDescription(descriptionRepository.getReferenceById(dto.getDescriptionId()));
        meal.setDailyConsume(dailyConsumeRepository.getReferenceById(dto.getDailyConsumeId()));
        return meal;
    }
}
