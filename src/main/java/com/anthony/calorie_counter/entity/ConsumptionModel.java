package com.anthony.calorie_counter.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_consumptions")
public class ConsumptionModel implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(nullable = false, name = "meal_id")
    private MealModel meal;
    @Column(nullable = false)
    private Integer grams;
    @JoinColumn(nullable = false, name = "aliment_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private AlimentModel aliment;

    public Integer getCalories() {
        return differenceFromBasePortion() * aliment.getCalories();
    }

    public Integer getTotalFat() {
        return differenceFromBasePortion() * aliment.getTotalFatAsNumber();
    }

    public Integer getProtein() {
        return differenceFromBasePortion() * aliment.getProteinAsNumber();
    }

    public Integer getCarbohydrate() {
        return differenceFromBasePortion() * aliment.getCarbohydrateAsNumber();
    }

    public Integer getFiber() {
        return differenceFromBasePortion() * aliment.getFiberAsNumber();
    }

    public Integer getSugars() {
        return differenceFromBasePortion() * aliment.getSugarsAsNumber();
    }

    private Integer differenceFromBasePortion() {
        return grams / aliment.getPortionAsNumber();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConsumptionModel that = (ConsumptionModel) o;
        return Objects.equals(id, that.id) && Objects.equals(grams, that.grams) && Objects.equals(aliment, that.aliment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, grams, aliment);
    }
}
