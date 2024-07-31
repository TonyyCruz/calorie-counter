package com.anthony.calorie_counter.repository;

import com.anthony.calorie_counter.entity.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID> {

    Optional<UserModel> findByEmail(String email);

    @Modifying
    @Query("UPDATE UserModel u SET u.password=?2 where u.id = ?1")
    void updatePasswordByUserId(UUID id, String password);
}
