package com.anthony.calorie_counter.utils.factories;

import com.anthony.calorie_counter.dto.request.aliment.AlimentCreateDto;
import com.anthony.calorie_counter.entity.AlimentModel;
import com.anthony.calorie_counter.utils.SimpleFake;

public class AlimentFactory {

    public static AlimentCreateDto alimentCreateDto() {
        AlimentCreateDto createDto = new AlimentCreateDto();
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

    public static AlimentModel alimentEntityFromDto(AlimentCreateDto createDto) {
        AlimentModel alimentModel = alimentCreateDto().toEntity();
        alimentModel.setId(SimpleFake.randomLong());
        return alimentModel;
    }

    public static AlimentModel createAlimentEntity() {
        return alimentEntityFromDto(alimentCreateDto());
    }

    public static AlimentCreateDto cloneMealCreateDto(AlimentCreateDto createDto) {
        AlimentCreateDto newCreateDto = new AlimentCreateDto();
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

    public static AlimentModel cloneAlimentModel(AlimentModel alimentModel) {
        AlimentModel newAlimentModel = new AlimentModel();
        newAlimentModel.setId(alimentModel.getId());
        newAlimentModel.setName(alimentModel.getName());
        newAlimentModel.setPortion(alimentModel.getPortion());
        newAlimentModel.setCalories(alimentModel.getCalories());
        newAlimentModel.setTotalFat(alimentModel.getTotalFat());
        newAlimentModel.setProtein(alimentModel.getProtein());
        newAlimentModel.setCarbohydrate(alimentModel.getCarbohydrate());
        newAlimentModel.setFiber(alimentModel.getFiber());
        newAlimentModel.setSugars(alimentModel.getSugars());
        return newAlimentModel;
    }
}
