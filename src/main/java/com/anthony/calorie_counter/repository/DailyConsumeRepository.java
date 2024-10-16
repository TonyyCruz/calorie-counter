package com.anthony.calorie_counter.repository;

import com.anthony.calorie_counter.entity.DailyConsumeModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DailyConsumeRepository extends JpaRepository<DailyConsumeModel, Long> {
    List<DailyConsumeModel> findAllByUserId(UUID userId);
}
