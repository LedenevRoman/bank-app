package com.training.rledenev.service.impl;

import com.training.rledenev.service.CurrencyApiRequestService;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

@Service
public class CurrencyApiRequestServiceNBP implements CurrencyApiRequestService {
    private static final String NATIONAL_BANK_POLAND_API_URL = "http://api.nbp.pl/api/exchangerates/rates/A/";

    @Override
    public JSONObject getCurrencyJsonObject(String currency) throws IOException {
        URL url = new URL(NATIONAL_BANK_POLAND_API_URL + currency);
        Scanner scanner = new Scanner((InputStream) url.getContent());
        StringBuilder result = new StringBuilder();
        while (scanner.hasNext()) {
            result.append(scanner.nextLine());
        }
        return new JSONObject(result.toString());
    }
}
