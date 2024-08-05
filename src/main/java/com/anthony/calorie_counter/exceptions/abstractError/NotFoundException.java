package com.anthony.calorie_counter.exceptions.abstractError;

public abstract class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
