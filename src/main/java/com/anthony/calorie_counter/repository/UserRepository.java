package com.anthony.calorie_counter.repository;

import com.anthony.calorie_counter.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);

    @Modifying
    @Query("UPDATE User u SET u.password=?2 where u.id = ?1")
    void updatePasswordById(String id, String password);
}
