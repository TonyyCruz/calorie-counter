package com.anthony.calorie_counter.exceptions;

import com.anthony.calorie_counter.exceptions.abstractExeptions.NotFound;

public class EntityDataNotFoundException extends NotFound {
    public EntityDataNotFoundException(String message) {
        super(message);
    }
}
