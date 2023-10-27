package com.training.rledenev.exceptions;

public class AccountNotFoundException extends EntityNotFoundException {
    public AccountNotFoundException(String message) {
        super(message);
    }
}
