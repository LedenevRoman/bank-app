package com.training.rledenev.bankapp.controllers;

import com.training.rledenev.bankapp.dto.ErrorData;
import com.training.rledenev.bankapp.exceptions.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@RestControllerAdvice
public class ExceptionControllerAdvisor {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorData> handleRuntimeException(ProductNotFoundException exception) {
        ErrorData errorModel = new ErrorData(HttpStatus.NO_CONTENT,
                exception.getMessage(), Arrays.toString(exception.getStackTrace()));
        return new ResponseEntity<>(errorModel, HttpStatus.NO_CONTENT);
    }
}
