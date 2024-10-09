package com.anthony.calorie_counter.entity;

import com.anthony.calorie_counter.enums.DescriptionName;
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
    @Column(nullable = false, name = "meal_name_id")
    private DescriptionName descriptionName;
    @Setter(AccessLevel.NONE)
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "meal")
    private Set<ConsumptionModel> consumptions = new HashSet<>();

    public void addConsumption(ConsumptionModel consumptionModel) {
        consumptions.add(consumptionModel);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MealModel mealModel = (MealModel) o;
        return Objects.equals(id, mealModel.id) && Objects.equals(descriptionName, mealModel.descriptionName) && Objects.equals(consumptions, mealModel.consumptions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, descriptionName, consumptions);
    }
}
