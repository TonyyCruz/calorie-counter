package com.anthony.calorie_counter.exceptions.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FieldErrorMessage implements Serializable {
    private String fieldName;
    private String error;
}
