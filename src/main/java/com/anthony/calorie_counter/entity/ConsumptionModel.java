package com.anthony.calorie_counter.entity;

import com.anthony.calorie_counter.exceptions.InvalidArgumentException;
import com.anthony.calorie_counter.exceptions.messages.ExceptionMessages;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_consumptions")
public class ConsumptionModel implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "meal_id")
    private MealModel meal;
    @Column(nullable = false)
    private Integer grams;
    @JoinColumn(nullable = false, name = "aliment_id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private AlimentModel aliment;

    public Integer getCalories() {
        return Math.round(differenceFromBasePortion() * aliment.getCalories());
    }

    public Float getTotalFat() {
        return formatTwoDecimals(differenceFromBasePortion() * aliment.getTotalFatAsNumber());
    }

    public Float getProtein() {
        return formatTwoDecimals(differenceFromBasePortion() * aliment.getProteinAsNumber());
    }

    public Float getCarbohydrate() {
        return formatTwoDecimals(differenceFromBasePortion() * aliment.getCarbohydrateAsNumber());
    }

    public Float getFiber() {
        return formatTwoDecimals(differenceFromBasePortion() * aliment.getFiberAsNumber());
    }

    public Float getSugars() {
        return formatTwoDecimals(differenceFromBasePortion() * aliment.getSugarsAsNumber());
    }

    private Float differenceFromBasePortion() {
        return formatTwoDecimals(grams / aliment.getPortionAsNumber());
    }

    private Float formatTwoDecimals(Float value) {
        try {
            String formatedValue = String.format("%.2f", value);
            return Float.valueOf(formatedValue);
        } catch (NumberFormatException e) {
            throw new InvalidArgumentException(ExceptionMessages.INVALID_ARGUMENTATION + value);
        }
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

    @Override
    public String toString() {
        return "ConsumptionModel{" +
                "id=" + id +
                ", mealId=" + meal.getId() +
                ", grams=" + grams +
                ", aliment=" + aliment +
                '}';
    }
}
