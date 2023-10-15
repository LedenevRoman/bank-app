package com.training.rledenev.services.impl;

import com.training.rledenev.entity.enums.CurrencyCode;
import com.training.rledenev.exceptions.RequestApiException;
import com.training.rledenev.services.CurrencyService;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Scanner;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    private static final String NATIONAL_BANK_POLAND_API_URL = "http://api.nbp.pl/api/exchangerates/rates/A/";
    @Override
    public BigDecimal getRateOfCurrency(String currencyCode) {
        currencyCode = currencyCode.toUpperCase();
        if (CurrencyCode.PLN.toString().equals(currencyCode)) {
            return BigDecimal.valueOf(1);
        }
        JSONObject currencyJson;
        try {
            currencyJson = getCurrencyJsonObject(currencyCode);
        } catch (IOException e) {
            throw new RequestApiException(e.getMessage());
        }
        JSONObject subObject = currencyJson.getJSONArray("rates").getJSONObject(0);
        return BigDecimal.valueOf(subObject.getDouble("mid"));
    }

    private static JSONObject getCurrencyJsonObject(String message) throws IOException {
        URL url = new URL(NATIONAL_BANK_POLAND_API_URL + message);
        Scanner scanner = new Scanner((InputStream) url.getContent());
        StringBuilder result = new StringBuilder();
        while (scanner.hasNext()) {
            result.append(scanner.nextLine());
        }
        return new JSONObject(result.toString());
    }
}
