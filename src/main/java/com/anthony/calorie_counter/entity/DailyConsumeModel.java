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
@Table(name = "tb_daily_meals")
public class DailyMealsModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private UserModel user;
    @Column(nullable = false)
    private Instant date;
    @Column(nullable = false)
    @Setter(AccessLevel.NONE)
    @OneToMany(fetch = FetchType.EAGER)
    private Set<MealModel> meals;

    public void addMeal(MealModel meal) {
        meals.add(meal);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DailyMealsModel that = (DailyMealsModel) o;
        return Objects.equals(id, that.id) && Objects.equals(user, that.user) && Objects.equals(date, that.date) && Objects.equals(meals, that.meals);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, date, meals);
    }
}
