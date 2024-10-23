package com.anthony.calorie_counter.exceptions;

import com.anthony.calorie_counter.exceptions.abstractExeptions.BadRequest;

public class InvalidReferencedDataException extends BadRequest {
    public InvalidReferencedDataException(String msg) { super(msg); }
}
