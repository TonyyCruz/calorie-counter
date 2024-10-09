package com.anthony.calorie_counter.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Entity
@ToString
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

    public Integer getPortionAsNumber() {
        return getValueAsNumber(getPortion());
    }

    public Integer getTotalFatAsNumber() {
        return getValueAsNumber(getTotalFat());
    }

    public Integer getProteinAsNumber() {
        return getValueAsNumber(getProtein());
    }

    public Integer getCarbohydrateAsNumber() {
        return getValueAsNumber(getCarbohydrate());
    }

    public Integer getFiberAsNumber() {
        return getValueAsNumber(getFiber());
    }

    public Integer getSugarsAsNumber() {
        return getValueAsNumber(getSugars());
    }

    private Integer getValueAsNumber(String value) {
        value = value.replace("g", "");
        return Integer.valueOf(value);
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
}
