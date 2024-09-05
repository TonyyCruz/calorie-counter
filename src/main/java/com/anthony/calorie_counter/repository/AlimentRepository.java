package com.anthony.calorie_counter.repository;

import com.anthony.calorie_counter.entity.AlimentModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AlimentRepository extends JpaRepository<AlimentModel, Long> {
    List<AlimentModel> findByNameContainingIgnoreCase(String name);
}
