package com.anthony.calorie_counter.entity;

import com.anthony.calorie_counter.enums.MealName;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_meals")
public class MealModel implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private MealName mealName;
    @Setter(AccessLevel.NONE)
    @OneToMany(fetch = FetchType.EAGER)
    private Set<ConsumptionModel> consumptions = new HashSet<>();

    public void addConsumption(ConsumptionModel consumptionModel) {
        consumptions.add(consumptionModel);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MealModel mealModel = (MealModel) o;
        return Objects.equals(id, mealModel.id) && Objects.equals(mealName, mealModel.mealName) && Objects.equals(consumptions, mealModel.consumptions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mealName, consumptions);
    }
}
