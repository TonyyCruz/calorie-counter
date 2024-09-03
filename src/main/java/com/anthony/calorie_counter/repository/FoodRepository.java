package com.anthony.calorie_counter.repository;

import com.anthony.calorie_counter.entity.MealModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<MealModel, Long> {
}
