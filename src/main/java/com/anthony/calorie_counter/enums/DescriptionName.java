package com.anthony.calorie_counter.enums;

import lombok.Getter;

@Getter
public enum DescriptionName {
    BREAKFAST(1L),
    SECOND_BREAKFAST(2L),
    BRUNCH(3L),
    ELEVENSES(4L),
    LUNCH(5L),
    AFTER_LUNCH(6L),
    SNACK(7L),
    DINNER(8L),
    AFTER_DINNER(9L),
    OTHER(10L);

    private final Long description;

    DescriptionName(Long id) {this.description = id; }
}
