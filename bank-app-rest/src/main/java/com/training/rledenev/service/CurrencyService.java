package com.training.rledenev.service;

import java.math.BigDecimal;

public interface CurrencyService {
    BigDecimal getRateOfCurrency(String currencyCode);
}
