package com.anthony.calorie_counter.exceptions;

import com.anthony.calorie_counter.exceptions.abstractExeptions.NotFound;

public class EntityDataNotFound extends NotFound {
    public EntityDataNotFound(String message) {
        super(message);
    }
}
