package com.anthony.calorie_counter.repository;

import com.anthony.calorie_counter.entity.DescriptionModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DescriptionRepository extends JpaRepository<DescriptionModel, Long> {
}
