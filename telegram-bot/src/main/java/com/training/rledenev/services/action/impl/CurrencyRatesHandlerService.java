package com.training.rledenev.services.action.impl;

import com.training.rledenev.entity.enums.CurrencyCode;
import com.training.rledenev.entity.enums.Role;
import com.training.rledenev.services.CurrencyService;
import com.training.rledenev.services.action.ActionMessageHandlerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.training.rledenev.services.util.BotUtils.*;

@RequiredArgsConstructor
@Service
public class CurrencyRatesHandlerService implements ActionMessageHandlerService {
    private final CurrencyService currencyService;

    @Override
    public SendMessage handleMessage(long chatId, String message, Role role) {
        List<String> currenciesWithoutDefaultCurrency = getAllCurrencies();
        List<String> currencyButtons = getCurrencyButtons(currenciesWithoutDefaultCurrency);
        if (message.equals(CURRENCY_RATES)) {
            return createSendMessageWithButtons(chatId, SELECT_CURRENCY, currencyButtons);
        }
        if (currenciesWithoutDefaultCurrency.contains(message)) {
            Double rate = currencyService.getRateOfCurrency(message).doubleValue();
            String currencyName = CurrencyCode.valueOf(message).getCurrencyName();
            LocalDate date = LocalDate.now();
            return createSendMessageWithButtons(chatId, String.format(OFFICIAL_CURRENCY_RATE, currencyName,
                    date, date, date, rate, message), currencyButtons);
        }
        return createSendMessageWithButtons(chatId, UNKNOWN_CURRENCY_CODE, currencyButtons);
    }

    private static List<String> getAllCurrencies() {
        return Arrays.stream(CurrencyCode.values())
                .skip(1)
                .map(Enum::toString)
                .collect(Collectors.toList());
    }

    private List<String> getCurrencyButtons(List<String> currenciesWithoutDefaultCurrency) {
        List<String> currencyButtons = new ArrayList<>(currenciesWithoutDefaultCurrency);
        currencyButtons.add(BACK);
        return currencyButtons;
    }
}
