package com.anthony.calorie_counter.exceptions;

import com.anthony.calorie_counter.exceptions.abstractError.UnauthorizedException;

public class InvalidCredentialsException extends UnauthorizedException {
    public InvalidCredentialsException(String msg) { super(msg); }
}
