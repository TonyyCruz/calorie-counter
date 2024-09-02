package com.anthony.calorie_counter.exceptions;

import com.anthony.calorie_counter.exceptions.abstractExeptions.Forbidden;

public class ForbiddenRequest extends Forbidden {
    public ForbiddenRequest(String msg) { super(msg); }
}
