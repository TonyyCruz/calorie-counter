package com.anthony.calorie_counter.repository;

import com.anthony.calorie_counter.entity.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleModel, Long> {}
