package com.anthony.calorie_counter.exceptions.abstractError;

public abstract class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String msg) { super(msg); }
}
