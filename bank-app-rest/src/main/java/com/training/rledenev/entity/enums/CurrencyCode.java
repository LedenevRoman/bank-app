package com.training.rledenev.entity.enums;

import lombok.Getter;

@Getter
public enum CurrencyCode {
    PLN("Zloty"),
    USD("US Dollar"),
    EUR("Euro"),
    GBP("Pound Sterling"),
    CHF("Swiss Franc"),
    HUF("Forint"),
    UAH("Hryvnia"),
    CZK("Czech Koruna"),
    DKK("Danish Krone"),
    NOK("Norwegian Krone"),
    SEK("Swedish Krona"),
    CNY("Yuan Renminbi"),
    JPY("Yen"),
    ISK("Iceland Krona"),
    ILS("New Israeli Sheqel"),
    TRY("Turkish Lira");

    private final String currencyName;

    CurrencyCode(String currencyName) {
        this.currencyName = currencyName;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
