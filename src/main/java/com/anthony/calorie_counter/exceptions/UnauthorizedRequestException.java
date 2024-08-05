package com.anthony.calorie_counter.exceptions;

import com.anthony.calorie_counter.exceptions.abstractError.UnauthorizedException;

public class UnauthorizedRequestException extends UnauthorizedException {
    public UnauthorizedRequestException(String msg) { super(msg); }
}
