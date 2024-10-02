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
    @Column(nullable = false)
    private Integer grams;
    @ManyToOne(fetch = FetchType.EAGER)
    private AlimentModel aliment;

    public Integer getCalories() {
        return differenceFromPortion() * aliment.getCalories();
    }

    public Integer getTotalFat() {
        return differenceFromPortion() * aliment.getTotalFatAsNumber();
    }

    public Integer getProtein() {
        return differenceFromPortion() * aliment.getProteinAsNumber();
    }

    public Integer getCarbohydrate() {
        return differenceFromPortion() * aliment.getCarbohydrateAsNumber();
    }

    public Integer getFiber() {
        return differenceFromPortion() * aliment.getFiberAsNumber();
    }

    public Integer getSugars() {
        return differenceFromPortion() * aliment.getSugarsAsNumber();
    }

    private Integer differenceFromPortion() {
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
