package com.training.rledenev.bankapp.services.bot.action.impl;

import com.training.rledenev.bankapp.entity.enums.CurrencyCode;
import com.training.rledenev.bankapp.entity.enums.Role;
import com.training.rledenev.bankapp.services.ProductService;
import com.training.rledenev.bankapp.services.bot.action.ActionMessageHandlerService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.training.rledenev.bankapp.services.bot.impl.BotUtils.*;

@Service
public class CurrencyRatesHandlerService implements ActionMessageHandlerService {
    private final ProductService productService;

    public CurrencyRatesHandlerService(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public SendMessage handleMessage(long chatId, String message, Role role) {
        List<String> currenciesWithoutDefaultCurrency = Arrays.stream(CurrencyCode.values())
                .skip(1)
                .map(Enum::toString)
                .collect(Collectors.toList());
        List<String> currencyButtons = new ArrayList<>(currenciesWithoutDefaultCurrency);
        currencyButtons.add(BACK);
        if (message.equals(CURRENCY_RATES)) {
            return createSendMessageWithButtons(chatId, SELECT_CURRENCY, currencyButtons);
        }
        if (currenciesWithoutDefaultCurrency.contains(message)) {
            Double rate = productService.getRateOfCurrency(message).doubleValue();
            String currencyName = CurrencyCode.valueOf(message).getCurrencyName();
            LocalDate date = LocalDate.now();
            return createSendMessageWithButtons(chatId, String.format(OFFICIAL_CURRENCY_RATE, currencyName,
                    date, date, date, rate, message), currencyButtons);
        }
        return createSendMessageWithButtons(chatId, UNKNOWN_CURRENCY_CODE, currencyButtons);
    }
}
