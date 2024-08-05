package com.anthony.calorie_counter.exceptions;

import com.anthony.calorie_counter.exceptions.abstractError.NotFoundException;

public class EntityDataNotFoundException extends NotFoundException {
    public EntityDataNotFoundException(String message) {
        super(message);
    }
}
