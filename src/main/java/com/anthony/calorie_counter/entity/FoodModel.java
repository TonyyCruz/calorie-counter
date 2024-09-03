package com.anthony.calorie_counter.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_foods")
public class FoodModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FoodModel foodModel = (FoodModel) o;
        return Objects.equals(id, foodModel.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
