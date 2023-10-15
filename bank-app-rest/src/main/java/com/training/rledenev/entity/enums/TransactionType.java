package com.training.rledenev.entity.enums;

import lombok.Getter;

@Getter
public enum TransactionType {
    TRANSFER("Transfer"),
    PAYMENT("Payment"),
    CASH("Cash"),
    DEPOSIT("Deposit");

    private final String name;

    TransactionType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
