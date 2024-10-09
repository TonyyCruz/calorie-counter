package com.anthony.calorie_counter.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_daily_consume")
public class DailyConsumeModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private UserModel user;
    @Column(nullable = false, unique = true)
    private Instant date;
    @Setter(AccessLevel.NONE)
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "dailyConsume")
    private Set<MealModel> meals;

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
}
