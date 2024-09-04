package com.anthony.calorie_counter.repository;

import com.anthony.calorie_counter.entity.AlimentModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlimentRepository extends JpaRepository<AlimentModel, Long> {
}
