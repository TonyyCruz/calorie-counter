package com.anthony.calorie_counter.enums;

import lombok.Getter;

@Getter
public enum UserRole {
    ROLE_USER(1L),
    ROLE_ADMIN(2L);

    private final Long role;

    UserRole(Long role) { this.role = role; }
}
