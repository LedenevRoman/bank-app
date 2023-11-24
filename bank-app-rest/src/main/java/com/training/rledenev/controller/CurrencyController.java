package com.training.rledenev.controller;

import com.training.rledenev.entity.enums.CurrencyCode;
import com.training.rledenev.service.CurrencyService;
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
    public BigDecimal getCurrencyRate(@PathVariable(name = "currencyCode") CurrencyCode currencyCode) {
        return currencyService.getRateOfCurrency(currencyCode.toString());
    }
}
