package com.anthony.calorie_counter.enums;

import lombok.Getter;

@Getter
public enum UserRole {
    USER("user"),
    ADMIN("admin");

    private final String role;

    UserRole(String role) { this.role = role; }
}
