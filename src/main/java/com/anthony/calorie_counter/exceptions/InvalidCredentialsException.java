package com.anthony.calorie_counter.exceptions;

import com.anthony.calorie_counter.exceptions.abstractError.BadRequest;

public class InvalidCredentialsException extends BadRequest {
    public InvalidCredentialsException(String msg) { super(msg); }
}
