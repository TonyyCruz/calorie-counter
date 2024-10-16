package com.anthony.calorie_counter.entity;

import com.anthony.calorie_counter.enums.DescriptionName;
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
@Table(name = "tb_descriptions")
public class DescriptionModel implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "description", nullable = false, unique = true)
    private DescriptionName descriptionName;

    public DescriptionModel(DescriptionName descriptionName) {
        this.id = descriptionName.getDescription();
        this.descriptionName = descriptionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DescriptionModel that = (DescriptionModel) o;
        return Objects.equals(id, that.id) && descriptionName == that.descriptionName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, descriptionName);
    }
}
