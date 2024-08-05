package com.anthony.calorie_counter.exceptions;

import com.anthony.calorie_counter.exceptions.abstractError.BadRequest;

public class AuthenticationDataException extends BadRequest {
    public AuthenticationDataException(String msg) { super(msg); }
}
