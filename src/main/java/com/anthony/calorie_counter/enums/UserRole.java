package com.anthony.calorie_counter.enums;

import lombok.Getter;

@Getter
public enum UserRole {
    ROLE_ADMIN(1),
    ROLE_USER(2);

    private final int role;

    UserRole(int role) { this.role = role; }
}
