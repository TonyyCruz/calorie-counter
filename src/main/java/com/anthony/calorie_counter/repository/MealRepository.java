package com.anthony.calorie_counter.repository;

import com.anthony.calorie_counter.entity.MealModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MealRepository extends JpaRepository<MealModel, Long> {
    List<MealModel> findAllByDailyConsumeId(Long dailyConsumeId);
}
