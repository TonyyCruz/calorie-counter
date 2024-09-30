package com.anthony.calorie_counter.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_Consumption")
public class ConsumptionModel {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Integer grams;
    @ManyToOne(fetch = FetchType.EAGER)
    private AlimentModel aliment;

    public Integer getCalories() {
        return getQuantityDifference() * aliment.getCalories();
    }

    public Integer getTotalFat() {
        return getQuantityDifference() * aliment.getTotalFatAsNumber();
    }

    public Integer getProtein() {
        return getQuantityDifference() * aliment.getProteinAsNumber();
    }

    public Integer getCarbohydrate() {
        return getQuantityDifference() * aliment.getCarbohydrateAsNumber();
    }

    public Integer getFiber() {
        return getQuantityDifference() * aliment.getFiberAsNumber();
    }

    public Integer getSugars() {
        return getQuantityDifference() * aliment.getSugarsAsNumber();
    }

    private Integer getQuantityDifference() {
        return grams / aliment.getPortionAsNumber();
    }
}
