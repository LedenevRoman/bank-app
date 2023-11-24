package com.training.rledenev.exception;

import org.springframework.security.access.AccessDeniedException;

public class NotOwnerException extends AccessDeniedException {
    public NotOwnerException(String message) {
        super(message);
    }
}
