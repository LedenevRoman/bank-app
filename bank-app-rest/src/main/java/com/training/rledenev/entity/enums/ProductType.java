package com.training.rledenev.entity.enums;

import lombok.Getter;

@Getter
public enum ProductType {
    LOAN("Loan"),
    DEPOSIT("Deposit"),
    DEBIT_CARD("Debit card"),
    CREDIT_CARD("Credit card");

    private final String name;

    ProductType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
