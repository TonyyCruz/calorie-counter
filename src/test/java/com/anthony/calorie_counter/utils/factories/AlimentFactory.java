package com.anthony.calorie_counter.utils.factories;

import com.anthony.calorie_counter.entity.dto.request.AlimentDto;
import com.anthony.calorie_counter.entity.AlimentModel;
import com.anthony.calorie_counter.utils.SimpleFake;

public class AlimentFactory {

    public static AlimentDto alimentDto() {
        AlimentDto createDto = new AlimentDto();
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

    public static AlimentModel alimentEntityFromDto(AlimentDto alimentDto) {
        AlimentModel alimentModel = alimentDto().toEntity();
        alimentModel.setId(SimpleFake.randomLong());
        return alimentModel;
    }

    public static AlimentModel createAlimentEntity() {
        return alimentEntityFromDto(alimentDto());
    }

    public static AlimentDto cloneAlimentDto(AlimentDto alimentDto) {
        AlimentDto newCreateDto = new AlimentDto();
        newCreateDto.setName(alimentDto.getName());
        newCreateDto.setPortion(alimentDto.getPortion());
        newCreateDto.setCalories(alimentDto.getCalories());
        newCreateDto.setTotalFat(alimentDto.getTotalFat());
        newCreateDto.setProtein(alimentDto.getProtein());
        newCreateDto.setCarbohydrate(alimentDto.getCarbohydrate());
        newCreateDto.setFiber(alimentDto.getFiber());
        newCreateDto.setSugars(alimentDto.getSugars());
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
