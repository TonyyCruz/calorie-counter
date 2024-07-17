package com.anthony.calorie_counter.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;


@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_roles")
public class Role implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String authority;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}


