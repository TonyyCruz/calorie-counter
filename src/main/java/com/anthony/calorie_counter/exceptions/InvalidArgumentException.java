package com.anthony.calorie_counter.exceptions;

import com.anthony.calorie_counter.exceptions.abstractError.BadRequest;

public class InvalidArgumentException extends BadRequest {
    public InvalidArgumentException(String msg) { super(msg); }
}
