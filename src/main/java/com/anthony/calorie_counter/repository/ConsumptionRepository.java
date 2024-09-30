package com.anthony.calorie_counter.repository;

import com.anthony.calorie_counter.entity.AlimentModel;
import com.anthony.calorie_counter.entity.ConsumptionModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsumptionRepository extends JpaRepository<ConsumptionModel, Long> {
}
