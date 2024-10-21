package com.anthony.calorie_counter.exceptions.abstractExeptions;

public abstract class InvalidArgument extends RuntimeException {
    public InvalidArgument(String message) {
        super(message);
    }
}
