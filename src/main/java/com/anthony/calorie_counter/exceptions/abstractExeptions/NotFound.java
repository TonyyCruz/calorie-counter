package com.anthony.calorie_counter.exceptions.abstractExeptions;

public abstract class NotFound extends RuntimeException {
    public NotFound(String message) {
        super(message);
    }
}
