package com.anthony.calorie_counter.exceptions.abstractExeptions;

public abstract class Unauthorized extends RuntimeException {
    public Unauthorized(String msg) { super(msg); }
}
