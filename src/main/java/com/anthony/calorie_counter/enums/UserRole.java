package com.anthony.calorie_counter.enums;

import com.anthony.calorie_counter.exceptions.InvalidArgumentException;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum UserRole {
    ROLE_USER(1L),
    ROLE_ADMIN(2L);

    private final Long role;

    UserRole(Long role) { this.role = role; }

    public static UserRole getRoleFrom(String roleString) {
        try {
            return UserRole.valueOf(roleString.toUpperCase());
        } catch (IllegalArgumentException e) {
            List<String> roles = Arrays.stream(UserRole.values()).map(Enum::name).toList();
            throw new InvalidArgumentException(
                    "\""
                    + roleString
                    + "\" is not a valid role. Please use one of the following: "
                    + roles
            );
        }
    }
}
