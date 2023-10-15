package com.training.rledenev.services;

import java.math.BigDecimal;

public interface CurrencyService {
    BigDecimal getRateOfCurrency(String currencyCode);
}
