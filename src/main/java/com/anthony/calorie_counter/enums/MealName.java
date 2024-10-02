package com.anthony.calorie_counter.enums;

import lombok.Getter;

@Getter
public enum MealName {
    BREAKFAST(1L),
    LUNCH(2L),
    DINNER(3L),
    SNACK(4L),
    OTHER(5L);

    private final Long mealId;

    MealName(Long id) {this.mealId = id; }
}
