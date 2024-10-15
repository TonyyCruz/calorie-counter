package com.anthony.calorie_counter.entity;

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
    @JoinColumn(name = "description_id", nullable = false)
    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.DETACH)
    private DescriptionModel description;
    @JoinColumn(name = "daily_consume_id", nullable = false)
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private DailyConsumeModel dailyConsume;
    @Setter(AccessLevel.NONE)
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "meal", cascade = CascadeType.ALL)
    private Set<ConsumptionModel> consumptions = new HashSet<>();

    public void addConsumption(ConsumptionModel consumptionModel) {
        consumptions.add(consumptionModel);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MealModel mealModel = (MealModel) o;
        return Objects.equals(id, mealModel.id) && Objects.equals(description, mealModel.description) && Objects.equals(consumptions, mealModel.consumptions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, consumptions);
    }
}
