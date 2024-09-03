package com.anthony.calorie_counter.utils.factories;

import com.anthony.calorie_counter.dto.request.meal.MealCreateDto;
import com.anthony.calorie_counter.entity.MealModel;
import com.anthony.calorie_counter.utils.SimpleFake;

public class FoodFactory {

    public static MealCreateDto foodCreateDto() {
        MealCreateDto createDto = new MealCreateDto();
        createDto.setName(SimpleFake.name());
        createDto.setPortion("100g");
        createDto.setCalories(SimpleFake.randomInteger());
        createDto.setTotalFat(SimpleFake.randomInteger() + "g");
        createDto.setProtein(SimpleFake.randomInteger() + "g");
        createDto.setCarbohydrate(SimpleFake.randomInteger() + "g");
        createDto.setFiber(SimpleFake.randomInteger() + "g");
        createDto.setSugars(SimpleFake.randomInteger() + "g");
        return createDto;
    }

    public static MealModel foodEntityFromDto(MealCreateDto createDto) {
        MealModel mealModel = foodCreateDto().toEntity();
        mealModel.setId(SimpleFake.randomLong());
        return mealModel;
    }

    public static MealModel createFoodEntity() {
        return foodEntityFromDto(foodCreateDto());
    }

    public static MealCreateDto cloneFoodCreateDto(MealCreateDto createDto) {
        MealCreateDto newCreateDto = new MealCreateDto();
        newCreateDto.setName(createDto.getName());
        newCreateDto.setPortion(createDto.getPortion());
        newCreateDto.setCalories(createDto.getCalories());
        newCreateDto.setTotalFat(createDto.getTotalFat());
        newCreateDto.setProtein(createDto.getProtein());
        newCreateDto.setCarbohydrate(createDto.getCarbohydrate());
        newCreateDto.setFiber(createDto.getFiber());
        newCreateDto.setSugars(createDto.getSugars());
        return newCreateDto;
    }

    public static MealModel cloneFoodModel(MealModel mealModel) {
        MealModel newMealModel = new MealModel();
        newMealModel.setId(mealModel.getId());
        newMealModel.setName(mealModel.getName());
        newMealModel.setPortion(mealModel.getPortion());
        newMealModel.setCalories(mealModel.getCalories());
        newMealModel.setTotalFat(mealModel.getTotalFat());
        newMealModel.setProtein(mealModel.getProtein());
        newMealModel.setCarbohydrate(mealModel.getCarbohydrate());
        newMealModel.setFiber(mealModel.getFiber());
        newMealModel.setSugars(mealModel.getSugars());
        return newMealModel;
    }
}
