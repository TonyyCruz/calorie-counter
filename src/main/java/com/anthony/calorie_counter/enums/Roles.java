package com.anthony.calorie_counter.enums;

import lombok.Getter;

@Getter
public enum Roles {
    USER("user"),
    ADMIN("admin");

    private final String role;

    Roles(String role) { this.role = role; }
}
