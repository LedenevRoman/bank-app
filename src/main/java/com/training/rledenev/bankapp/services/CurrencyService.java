package com.training.rledenev.bankapp.services;

import java.math.BigDecimal;

public interface CurrencyService {
    BigDecimal getRateOfCurrency(String currencyCode);
}
