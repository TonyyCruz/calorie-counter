package com.anthony.calorie_counter.exceptions.abstractExeptions;

public abstract class Forbidden extends RuntimeException {
    public Forbidden(String msg) { super(msg); }
}
