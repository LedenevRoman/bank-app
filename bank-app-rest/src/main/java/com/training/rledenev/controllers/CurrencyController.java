package com.training.rledenev.controllers;

import com.training.rledenev.services.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/currency")
public class CurrencyController {
    private final CurrencyService currencyService;

    @GetMapping("/{currencyCode}")
    public BigDecimal getCurrencyRate(@PathVariable(name = "currencyCode") String currencyCode) {
        return currencyService.getRateOfCurrency(currencyCode);
    }
}
