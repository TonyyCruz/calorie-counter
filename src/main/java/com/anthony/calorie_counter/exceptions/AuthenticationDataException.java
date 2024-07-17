package com.anthony.calorie_counter.exceptions;

import org.springframework.security.core.AuthenticationException;

public class AuthenticationDataException extends RuntimeException {
    public AuthenticationDataException(String msg) { super(msg); }
}
