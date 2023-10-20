package com.training.rledenev.controllers;

import com.training.rledenev.dto.ErrorData;
import com.training.rledenev.exceptions.ProductNotFoundException;
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

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorData> handleProductNotFoundException(ProductNotFoundException exception) {
        ErrorData errorData = new ErrorData(HttpStatus.NO_CONTENT, LocalDateTime.now(),
                exception.getMessage(), Arrays.toString(exception.getStackTrace()));
        return new ResponseEntity<>(errorData, HttpStatus.NO_CONTENT);
    }
}
