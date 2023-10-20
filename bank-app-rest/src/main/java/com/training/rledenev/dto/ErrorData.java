package com.training.rledenev.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ErrorData {
    private final HttpStatus httpStatus;
    private final LocalDateTime timestamp;
    private final String message;
    private final String details;
}
