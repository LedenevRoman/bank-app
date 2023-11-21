package com.training.rledenev.services;

import org.json.JSONObject;

import java.io.IOException;

public interface CurrencyApiRequestService {

    JSONObject getCurrencyJsonObject(String currency) throws IOException;
}
