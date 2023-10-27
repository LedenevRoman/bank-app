package com.training.rledenev.controllers;

import com.training.rledenev.dto.ErrorData;
import com.training.rledenev.exceptions.EntityNotFoundException;
import com.training.rledenev.exceptions.InsufficientFundsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Arrays;

@RestControllerAdvice
public class ExceptionControllerAdvisor {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorData> handleRuntimeException(RuntimeException exception) {
        ErrorData errorData = new ErrorData(HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now(),
                exception.getMessage(), Arrays.toString(exception.getStackTrace()));
        return new ResponseEntity<>(errorData, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorData> handleProductNotFoundException(EntityNotFoundException exception) {
        ErrorData errorData = new ErrorData(HttpStatus.NO_CONTENT, LocalDateTime.now(),
                exception.getMessage(), Arrays.toString(exception.getStackTrace()));
        return new ResponseEntity<>(errorData, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<ErrorData> handleProductNotFoundException(InsufficientFundsException exception) {
        ErrorData errorData = new ErrorData(HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                exception.getMessage(), Arrays.toString(exception.getStackTrace()));
        return new ResponseEntity<>(errorData, HttpStatus.NOT_ACCEPTABLE);
    }
}
