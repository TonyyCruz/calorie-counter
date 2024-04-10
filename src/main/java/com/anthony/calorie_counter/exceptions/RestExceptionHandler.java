package com.anthony.calorie_counter.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    ResponseEntity<ExceptionDetails> notFound(NotFoundException e, HttpServletRequest request) {
        ExceptionDetails exceptionDetails = new ExceptionDetails();
        exceptionDetails.setTitle("Bed request, resource not found.");
        exceptionDetails.setTimestamp(Instant.now());
        exceptionDetails.setStatus(HttpStatus.NOT_FOUND.value());
        exceptionDetails.setException(e.getMessage());
        exceptionDetails.setPath(request.getRequestURI());
        return ResponseEntity.status(exceptionDetails.getStatus()).body(exceptionDetails);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ExceptionDetails> argumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(objectError -> {
            String fieldName = objectError.getObjectName();
            String message = objectError.getDefaultMessage();
            errors.put(fieldName, message);
        });
        ExceptionDetails exceptionDetails = new ExceptionDetails();
        exceptionDetails.setTitle("Bad request, invalid argumentation.");
        exceptionDetails.setTimestamp(Instant.now());
        exceptionDetails.setStatus(HttpStatus.BAD_REQUEST.value());
        exceptionDetails.setException(e.getClass().toString());
        exceptionDetails.setPath(request.getRequestURI());
        exceptionDetails.setDetails(errors);
        return ResponseEntity.status(exceptionDetails.getStatus()).body(exceptionDetails);
    }
}