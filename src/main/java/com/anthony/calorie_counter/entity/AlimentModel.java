package com.anthony.calorie_counter.entity;

import com.anthony.calorie_counter.exceptions.InvalidArgumentException;
import com.anthony.calorie_counter.exceptions.messages.ExceptionMessages;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_aliments")
public class AlimentModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String portion;
    @Column(nullable = false)
    private Integer calories;
    @Column(nullable = false)
    private String totalFat;
    @Column(nullable = false)
    private String protein;
    @Column(nullable = false)
    private String  carbohydrate;
    @Column(nullable = false)
    private String  fiber;
    @Column(nullable = false)
    private String  sugars;
    @Setter(AccessLevel.NONE)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "aliment", cascade = CascadeType.ALL)
    private Set<ConsumptionModel> consumptions = new HashSet<>();

    public Float getPortionAsNumber() {
        return getValueAsNumber(getPortion());
    }

    public Float getTotalFatAsNumber() {
        return getValueAsNumber(getTotalFat());
    }

    public Float getProteinAsNumber() {
        return getValueAsNumber(getProtein());
    }

    public Float getCarbohydrateAsNumber() {
        return getValueAsNumber(getCarbohydrate());
    }

    public Float getFiberAsNumber() {
        return getValueAsNumber(getFiber());
    }

    public Float getSugarsAsNumber() {
        return getValueAsNumber(getSugars());
    }

    private Float getValueAsNumber(String value) {
        try {
            value = value.replace("g", "");
            return Float.valueOf(value);
        } catch (NumberFormatException e) {
            throw new InvalidArgumentException(ExceptionMessages.INVALID_ARGUMENTATION + value);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlimentModel alimentModel = (AlimentModel) o;
        return Objects.equals(id, alimentModel.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AlimentModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", portion='" + portion + '\'' +
                ", calories=" + calories +
                ", totalFat='" + totalFat + '\'' +
                ", protein='" + protein + '\'' +
                ", carbohydrate='" + carbohydrate + '\'' +
                ", fiber='" + fiber + '\'' +
                ", sugars='" + sugars + '\'' +
                '}';
    }
}
