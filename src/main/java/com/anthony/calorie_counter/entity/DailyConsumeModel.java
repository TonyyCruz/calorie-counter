package com.anthony.calorie_counter.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_daily_consume")
public class DailyConsumeModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(optional = false, cascade = CascadeType.DETACH)
    private UserModel user;
    @Column(nullable = false)
    private Instant date;
    @Setter(AccessLevel.NONE)
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "dailyConsume", cascade = CascadeType.ALL)
    private Set<MealModel> meals = new HashSet<>();

    public void addMeal(MealModel meal) {
        meals.add(meal);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DailyConsumeModel that = (DailyConsumeModel) o;
        return Objects.equals(id, that.id) && Objects.equals(user, that.user) && Objects.equals(date, that.date) && Objects.equals(meals, that.meals);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, date, meals);
    }

    @Override
    public String toString() {
        return "DailyConsumeModel{" +
                "id=" + id +
                ", userId=" + user.getId() +
                ", date=" + date +
                ", meals=" + meals +
                '}';
    }
}
