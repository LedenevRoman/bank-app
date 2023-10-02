package com.training.rledenev.bankapp.controllers;

import com.training.rledenev.bankapp.services.CurrencyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping(path = "/currency")
@AllArgsConstructor
public class CurrencyController {

    private final CurrencyService currencyService;

    @GetMapping("/{currencyCode}")
    public BigDecimal getCurrencyRate(@PathVariable(name = "currencyCode") String currencyCode) {
        return currencyService.getRateOfCurrency(currencyCode);
    }
}
