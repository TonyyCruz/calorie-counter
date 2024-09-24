package com.anthony.calorie_counter.exceptions;

import com.anthony.calorie_counter.exceptions.abstractExeptions.Forbidden;

public class ForbiddenRequestException extends Forbidden {
    public ForbiddenRequestException(String msg) { super(msg); }
}
