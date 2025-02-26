package com.anthony.calorie_counter.exceptions.handler;

import com.anthony.calorie_counter.exceptions.abstractExeptions.BadRequest;
import com.anthony.calorie_counter.exceptions.abstractExeptions.InvalidArgument;
import com.anthony.calorie_counter.exceptions.abstractExeptions.NotFound;
import com.anthony.calorie_counter.exceptions.abstractExeptions.Forbidden;
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

    @ExceptionHandler(NotFound.class)
    ResponseEntity<ExceptionDetails> notFound(NotFound e, HttpServletRequest request) {
        ExceptionDetails exceptionDetails = new ExceptionDetails();
        exceptionDetails.setTitle("Not found.");
        exceptionDetails.setTimestamp(Instant.now());
        exceptionDetails.setStatus(HttpStatus.NOT_FOUND.value());
        exceptionDetails.setException(e.getClass().toString());
        exceptionDetails.setPath(request.getRequestURI());
        exceptionDetails.addError("error", e.getMessage());
        return ResponseEntity.status(exceptionDetails.getStatus()).body(exceptionDetails);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ValidationDetails> invalidArgumentation(MethodArgumentNotValidException e, HttpServletRequest request) {
        ValidationDetails validationDetails = new ValidationDetails();
        validationDetails.setTitle("Bad request, invalid argumentation.");
        validationDetails.setTimestamp(Instant.now());
        validationDetails.setStatus(HttpStatus.BAD_REQUEST.value());
        validationDetails.setException(e.getClass().toString());
        validationDetails.setPath(request.getRequestURI());
        validationDetails.addError("InvalidFieldData", e.getMessage());
        e.getBindingResult().getFieldErrors().forEach(objectError -> {
            validationDetails.addFieldError(objectError.getField(), objectError.getDefaultMessage());
        });
        return ResponseEntity.status(validationDetails.getStatus()).body(validationDetails);
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

    @ExceptionHandler(BadRequest.class)
    ResponseEntity<ExceptionDetails> authenticationException(BadRequest e, HttpServletRequest request) {
        ExceptionDetails exceptionDetails = new ExceptionDetails();
        exceptionDetails.setTitle("Bad request, error with received data.");
        exceptionDetails.setTimestamp(Instant.now());
        exceptionDetails.setStatus(HttpStatus.BAD_REQUEST.value());
        exceptionDetails.setException(e.getClass().toString());
        exceptionDetails.setPath(request.getRequestURI());
        exceptionDetails.addError("error", e.getMessage());
        return ResponseEntity.status(exceptionDetails.getStatus()).body(exceptionDetails);
    }

    @ExceptionHandler(Forbidden.class)
    ResponseEntity<ExceptionDetails> unauthorizedException(Forbidden e, HttpServletRequest request) {
        ExceptionDetails exceptionDetails = new ExceptionDetails();
        exceptionDetails.setTitle("Unauthorized.");
        exceptionDetails.setTimestamp(Instant.now());
        exceptionDetails.setStatus(HttpStatus.FORBIDDEN.value());
        exceptionDetails.setException(e.getClass().toString());
        exceptionDetails.setPath(request.getRequestURI());
        exceptionDetails.addError("error", e.getMessage());
        return ResponseEntity.status(exceptionDetails.getStatus()).body(exceptionDetails);
    }

    @ExceptionHandler(InvalidArgument.class)
    ResponseEntity<ExceptionDetails> invalidArgumentException(InvalidArgument e, HttpServletRequest request) {
        ExceptionDetails exceptionDetails = new ExceptionDetails();
        exceptionDetails.setTitle("Invalid argument.");
        exceptionDetails.setTimestamp(Instant.now());
        exceptionDetails.setStatus(HttpStatus.BAD_REQUEST.value());
        exceptionDetails.setException(e.getClass().toString());
        exceptionDetails.setPath(request.getRequestURI());
        exceptionDetails.addError("error", e.getMessage());
        return ResponseEntity.status(exceptionDetails.getStatus()).body(exceptionDetails);
    }
}
