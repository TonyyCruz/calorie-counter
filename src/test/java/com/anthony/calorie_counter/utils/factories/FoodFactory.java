package com.anthony.calorie_counter.utils.factories;

import com.anthony.calorie_counter.dto.request.food.FoodCreateDto;
import com.anthony.calorie_counter.entity.FoodModel;
import com.anthony.calorie_counter.utils.SimpleFake;

public class FoodFactory {

    public static FoodCreateDto foodCreateDto() {
        FoodCreateDto createDto = new FoodCreateDto();
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

    public static FoodModel foodEntityFromDto(FoodCreateDto createDto) {
        FoodModel foodModel = foodCreateDto().toEntity();
        foodModel.setId(SimpleFake.randomLong());
        return foodModel;
    }

    public static FoodModel createFoodEntity() {
        return foodEntityFromDto(foodCreateDto());
    }

    public static FoodCreateDto cloneFoodCreateDto(FoodCreateDto createDto) {
        FoodCreateDto newCreateDto = new FoodCreateDto();
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

    public static FoodModel cloneFoodModel(FoodModel foodModel) {
        FoodModel newFoodModel = new FoodModel();
        newFoodModel.setId(foodModel.getId());
        newFoodModel.setName(foodModel.getName());
        newFoodModel.setPortion(foodModel.getPortion());
        newFoodModel.setCalories(foodModel.getCalories());
        newFoodModel.setTotalFat(foodModel.getTotalFat());
        newFoodModel.setProtein(foodModel.getProtein());
        newFoodModel.setCarbohydrate(foodModel.getCarbohydrate());
        newFoodModel.setFiber(foodModel.getFiber());
        newFoodModel.setSugars(foodModel.getSugars());
        return newFoodModel;
    }
}
