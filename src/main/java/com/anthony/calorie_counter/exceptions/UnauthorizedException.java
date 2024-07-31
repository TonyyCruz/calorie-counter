package com.anthony.calorie_counter.exceptions;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String msg) { super(msg); }
}
