package com.training.rledenev.bankapp.entity.enums;

import lombok.Getter;

@Getter
public enum ProductType {
    LOAN("Loan"),
    MORTGAGE("Mortgage"),
    DEPOSIT("Deposit"),
    CAR_LOAN("Car loan"),
    DEBIT_ACCOUNT("Debit account"),
    CREDIT_CARD("Credit account");

    private final String name;

    ProductType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
