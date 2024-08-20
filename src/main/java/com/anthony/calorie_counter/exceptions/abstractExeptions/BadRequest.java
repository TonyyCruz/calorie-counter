package com.anthony.calorie_counter.exceptions.abstractExeptions;

public abstract class BadRequest extends RuntimeException {
    public BadRequest(String msg) { super(msg); }
}
