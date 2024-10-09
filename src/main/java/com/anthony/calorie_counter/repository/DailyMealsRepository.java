package com.anthony.calorie_counter.repository;

import com.anthony.calorie_counter.entity.DailyConsumeModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyMealsRepository extends JpaRepository<DailyConsumeModel, Long> {
}
