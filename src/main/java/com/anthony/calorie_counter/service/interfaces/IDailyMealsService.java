package com.anthony.calorie_counter.service.interfaces;


import com.anthony.calorie_counter.entity.DailyConsumeModel;

public interface IDailyMealsService {

    DailyConsumeModel findDailyMealsById(Long id);

    DailyConsumeModel updateDailyMeals(Long id, DailyConsumeModel dailyMeals);

    void deleteDailyMealsById(Long id);
}
