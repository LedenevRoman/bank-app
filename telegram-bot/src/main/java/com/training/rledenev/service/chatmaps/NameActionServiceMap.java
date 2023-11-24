package com.training.rledenev.service.chatmaps;

import com.training.rledenev.service.action.ActionMessageHandlerService;
import com.training.rledenev.service.action.impl.AccountsMessageHandlerService;
import com.training.rledenev.service.action.impl.AgreementMessageHandlerService;
import com.training.rledenev.service.action.impl.CurrencyRatesHandlerService;
import com.training.rledenev.service.action.impl.ProductMessageHandlerService;
import com.training.rledenev.service.util.BotUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public final class NameActionServiceMap {
    private static final Map<String, ActionMessageHandlerService> NAME_ACTION_MAP = new ConcurrentHashMap<>();

    private final AgreementMessageHandlerService agreementsMessageHandlerService;
    private final ProductMessageHandlerService productMessageHandlerService;
    private final CurrencyRatesHandlerService currencyRatesHandlerService;
    private final AccountsMessageHandlerService accountsMessageHandlerService;

    @PostConstruct
    private void init() {
        NAME_ACTION_MAP.put(BotUtils.MY_ACCOUNTS, accountsMessageHandlerService);
        NAME_ACTION_MAP.put(BotUtils.NEW_AGREEMENTS, agreementsMessageHandlerService);
        NAME_ACTION_MAP.put(BotUtils.PRODUCTS, productMessageHandlerService);
        NAME_ACTION_MAP.put(BotUtils.CURRENCY_RATES, currencyRatesHandlerService);
    }

    public static boolean containsKey(String message) {
        return NAME_ACTION_MAP.containsKey(message);
    }

    public static void put(String name, ActionMessageHandlerService actionMessageHandlerService) {
        NAME_ACTION_MAP.put(name, actionMessageHandlerService);
    }

    public static ActionMessageHandlerService get(String name) {
        return NAME_ACTION_MAP.get(name);
    }

    public static void remove(String name) {
        NAME_ACTION_MAP.remove(name);
    }
}
