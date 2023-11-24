package com.training.rledenev.exception;

public class AccountNotFoundException extends EntityNotFoundException {
    public AccountNotFoundException(String message) {
        super(message);
    }
}
