package com.anthony.calorie_counter.exceptions.handler;

import com.anthony.calorie_counter.exceptions.NotFoundException;
import com.anthony.calorie_counter.exceptions.TokenCreateException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    ResponseEntity<ExceptionDetails> notFound(NotFoundException e, HttpServletRequest request) {
        ExceptionDetails exceptionDetails = new ExceptionDetails();
        exceptionDetails.setTitle("Bed request, resource not found.");
        exceptionDetails.setTimestamp(Instant.now());
        exceptionDetails.setStatus(HttpStatus.NOT_FOUND.value());
        exceptionDetails.setException(e.getClass().toString());
        exceptionDetails.setPath(request.getRequestURI());
        exceptionDetails.addError("notFound", e.getMessage());
        return ResponseEntity.status(exceptionDetails.getStatus()).body(exceptionDetails);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ValidationError> invalidArgumentation(MethodArgumentNotValidException e, HttpServletRequest request) {
        ValidationError validationError = new ValidationError();
        validationError.setTitle("Bad request, invalid argumentation.");
        validationError.setTimestamp(Instant.now());
        validationError.setStatus(HttpStatus.BAD_REQUEST.value());
        validationError.setException(e.getClass().toString());
        validationError.setPath(request.getRequestURI());
        validationError.addError("InvalidFieldData", e.getMessage());
        e.getBindingResult().getFieldErrors().forEach(objectError -> {
            validationError.addFieldError(objectError.getField(), objectError.getDefaultMessage());
        });
        return ResponseEntity.status(validationError.getStatus()).body(validationError);
    }

    @ExceptionHandler(DataAccessException.class)
    ResponseEntity<ExceptionDetails> dataAccessViolation(DataAccessException e, HttpServletRequest request) {
        ExceptionDetails exceptionDetails = new ExceptionDetails();
        exceptionDetails.setTitle("Conflicting data, received data already exists in the database.");
        exceptionDetails.setTimestamp(Instant.now());
        exceptionDetails.setStatus(HttpStatus.CONFLICT.value());
        exceptionDetails.setException(e.getClass().toString());
        exceptionDetails.setPath(request.getRequestURI());
        exceptionDetails.addError(e.getCause().toString(), e.getMessage());
        return ResponseEntity.status(exceptionDetails.getStatus()).body(exceptionDetails);
    }

    @ExceptionHandler(TokenCreateException.class)
    ResponseEntity<ExceptionDetails> validateException(TokenCreateException e, HttpServletRequest request) {
        ExceptionDetails exceptionDetails = new ExceptionDetails();
        exceptionDetails.setTitle("Validate Exception.");
        exceptionDetails.setTimestamp(Instant.now());
        exceptionDetails.setStatus(HttpStatus.FORBIDDEN.value());
        exceptionDetails.setException(e.getClass().toString());
        exceptionDetails.setPath(request.getRequestURI());
        exceptionDetails.addError(e.getCause().toString(), e.getMessage());
        return ResponseEntity.status(exceptionDetails.getStatus()).body(exceptionDetails);
    }
}
