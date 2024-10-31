package com.anthony.calorie_counter.repository;

import com.anthony.calorie_counter.entity.DailyConsumeModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DailyConsumeRepository extends JpaRepository<DailyConsumeModel, Long> {

    List<DailyConsumeModel> findAllByUserId(UUID userId);

    Optional<DailyConsumeModel> findByUserIdAndDate(UUID userId, Instant date);
}
