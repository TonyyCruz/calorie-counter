package com.anthony.calorie_counter.service.impl;

import com.anthony.calorie_counter.entity.MealModel;
import com.anthony.calorie_counter.repository.FoodRepository;
import com.anthony.calorie_counter.service.interfaces.IFoodService;
import org.springframework.stereotype.Service;

@Service
public class FoodService implements IFoodService {
    private final FoodRepository foodRepository;

    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @Override
    public MealModel create(MealModel food) {
        return foodRepository.save(food);
    }

    @Override
    public MealModel update(Long id, MealModel food) {
        MealModel current = foodRepository.getReferenceById(id);
        current.setName(food.getName());
        current.setPortion(food.getPortion());
        current.setCalories(food.getCalories());
        current.setTotalFat(food.getTotalFat());
        current.setProtein(food.getProtein());
        current.setCarbohydrate(food.getCarbohydrate());
        current.setFiber(food.getFiber());
        current.setSugars(food.getSugars());
        return foodRepository.save(food);
    }

    @Override
    public void delete(Long id) {
        foodRepository.deleteById(id);
    }
}
