package com.anthony.calorie_counter.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;

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
        String errors = exceptionMessageFormatter(e.getAllErrors());
        ExceptionDetails exceptionDetails = new ExceptionDetails();
        exceptionDetails.setTitle("Bed request, argument not valid.");
        exceptionDetails.setTimestamp(Instant.now());
        exceptionDetails.setStatus(HttpStatus.BAD_REQUEST.value());
        exceptionDetails.setException(errors);
        exceptionDetails.setPath(request.getRequestURI());
        return ResponseEntity.status(exceptionDetails.getStatus()).body(exceptionDetails);
    }

    private String exceptionMessageFormatter(List<ObjectError> errors) {
        String errorMessage = "Errors found %d: ".formatted(errors.size());
        for(ObjectError err : errors) {
            if (err.getDefaultMessage() != null) {
                errorMessage = errorMessage.concat("[" ).concat(err.getDefaultMessage()).concat("] ");
            }
        }
        return errorMessage.trim();
    }
}
