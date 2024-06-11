package com.anthony.calorie_counter.exceptions;

public class TokenCreateException extends RuntimeException {
    public TokenCreateException(String message, Exception e) {
        super(message, e);
    }
}
