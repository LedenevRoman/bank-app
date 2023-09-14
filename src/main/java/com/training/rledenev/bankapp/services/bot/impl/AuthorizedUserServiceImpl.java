package com.training.rledenev.bankapp.services.bot.impl;

import com.training.rledenev.bankapp.security.jwt.JwtFilter;
import com.training.rledenev.bankapp.security.jwt.JwtProvider;
import com.training.rledenev.bankapp.services.bot.AuthorizedUserService;
import com.training.rledenev.bankapp.services.bot.action.ActionMessageHandlerService;
import com.training.rledenev.bankapp.services.bot.action.impl.CurrencyRatesHandlerService;
import com.training.rledenev.bankapp.services.bot.action.impl.ProductMessageHandlerService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.training.rledenev.bankapp.services.bot.impl.BotUtils.*;

@Service
public class AuthorizedUserServiceImpl implements AuthorizedUserService {
    public static final Map<Long, String> CHAT_ID_ACTION_NAME_MAP = new HashMap<>();
    static final Map<String, ActionMessageHandlerService> NAME_ACTION_MAP = new HashMap<>();
    private final ProductMessageHandlerService productMessageHandlerService;
    private final CurrencyRatesHandlerService currencyRatesHandlerService;
    private final JwtProvider jwtProvider;
    private final JwtFilter jwtFilter;


    @PostConstruct
    private void init() {
        NAME_ACTION_MAP.put(PRODUCTS, productMessageHandlerService);
        NAME_ACTION_MAP.put(CURRENCY_RATES, currencyRatesHandlerService);
    }

    public AuthorizedUserServiceImpl(ProductMessageHandlerService productMessageHandlerService,
                                     CurrencyRatesHandlerService currencyRatesHandlerService, JwtProvider jwtProvider,
                                     JwtFilter jwtFilter) {
        this.productMessageHandlerService = productMessageHandlerService;
        this.currencyRatesHandlerService = currencyRatesHandlerService;
        this.jwtProvider = jwtProvider;
        this.jwtFilter = jwtFilter;
    }

    @Override
    public SendMessage handleRequests(String message, long chatId, String token) {
        if (jwtProvider.validateToken(token)) {
            jwtFilter.setCustomUserDetailsToSecurityContextHolder(token);
            if (message.equals(BACK)) {
                CHAT_ID_ACTION_NAME_MAP.remove(chatId);
                ProductMessageHandlerService.CHAT_ID_AGREEMENT_DTO_MAP.remove(chatId);
                SendMessage sendMessage = createSendMessage(chatId, SELECT_ACTION);
                return BotUtils.addButtonsToMessage(sendMessage, getListOfActions());
            }
            String actionName = CHAT_ID_ACTION_NAME_MAP.get(chatId);
            if (actionName != null) {
                return NAME_ACTION_MAP.get(actionName).handleMessage(chatId, message);
            } else if (NAME_ACTION_MAP.containsKey(message)) {
                CHAT_ID_ACTION_NAME_MAP.put(chatId, message);
                return NAME_ACTION_MAP.get(message).handleMessage(chatId, message);
            }
            SendMessage sendMessage = createSendMessage(chatId, UNKNOWN_INPUT_MESSAGE);
            return addButtonsToMessage(sendMessage, getListOfActions());
        } else {
            UpdateHandlerServiceImpl.CHAT_ID_TOKEN_MAP.remove(chatId);
            SendMessage sendMessage = createSendMessage(chatId, SESSION_CLOSED);
            return addButtonsToMessage(sendMessage, List.of(REGISTER_USER, LOG_IN));
        }
    }
}
