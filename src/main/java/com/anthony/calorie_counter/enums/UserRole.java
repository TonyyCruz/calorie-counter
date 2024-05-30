package com.anthony.calorie_counter.enums;

import lombok.Getter;

@Getter
public enum UserRole {
    USER(0),
    ADMIN(1);

    private final int role;

    UserRole(int role) { this.role = role; }
}
