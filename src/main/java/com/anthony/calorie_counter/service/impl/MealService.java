package com.anthony.calorie_counter.service.impl;

import com.anthony.calorie_counter.entity.MealModel;
import com.anthony.calorie_counter.exceptions.EntityDataNotFoundException;
import com.anthony.calorie_counter.exceptions.messages.ExceptionMessages;
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

    @Override
    public MealModel create(MealModel meal) {
        return mealRepository.save(meal);
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
    public MealModel updateById(Long id, MealModel meal) {
        try {
            MealModel current = mealRepository.getReferenceById(id);
            current.setDailyConsume(meal.getDailyConsume());
            current.setDescription(meal.getDescription());
            return mealRepository.save(current);
        } catch (EntityNotFoundException e) {
            throw new EntityDataNotFoundException(ExceptionMessages.DATA_NOT_FOUND_WITH_ID + id);
        }
    }

    @Override
    public void deleteById(Long id) {
        if (!mealRepository.existsById(id)) {
            throw new EntityDataNotFoundException(ExceptionMessages.DATA_NOT_FOUND_WITH_ID+ id);
        }
        mealRepository.deleteById(id);
    }
}
