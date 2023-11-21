package com.training.rledenev.services.impl;

import com.training.rledenev.entity.enums.CurrencyCode;
import com.training.rledenev.exceptions.RequestApiException;
import com.training.rledenev.services.CurrencyApiRequestService;
import com.training.rledenev.services.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
public class CurrencyServiceNBP implements CurrencyService {
    private final CurrencyApiRequestService currencyApiRequestService;
    @Override
    public BigDecimal getRateOfCurrency(String currencyCode) {
        currencyCode = currencyCode.toUpperCase();
        if (CurrencyCode.PLN.toString().equals(currencyCode)) {
            return BigDecimal.valueOf(1);
        }
        JSONObject currencyJson;
        try {
            currencyJson = currencyApiRequestService.getCurrencyJsonObject(currencyCode);
        } catch (IOException e) {
            throw new RequestApiException(e.getMessage());
        }
        JSONObject subObject = currencyJson.getJSONArray("rates").getJSONObject(0);
        return BigDecimal.valueOf(subObject.getDouble("mid"));
    }
}
