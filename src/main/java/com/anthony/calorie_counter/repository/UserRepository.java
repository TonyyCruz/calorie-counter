package com.anthony.calorie_counter.repository;

import com.anthony.calorie_counter.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);

    @Modifying
    @Query("DELETE from User u where u.email = ?1")
    void deleteByEmail(String email);
}
