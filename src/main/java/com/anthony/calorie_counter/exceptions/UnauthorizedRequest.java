package com.anthony.calorie_counter.exceptions;

import com.anthony.calorie_counter.exceptions.abstractExeptions.Unauthorized;

public class UnauthorizedRequest extends Unauthorized {
    public UnauthorizedRequest(String msg) { super(msg); }
}
