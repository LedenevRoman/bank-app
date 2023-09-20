package com.training.rledenev.bankapp.services.bot.impl;

import com.training.rledenev.bankapp.entity.enums.Role;
import com.training.rledenev.bankapp.provider.UserProvider;
import com.training.rledenev.bankapp.security.jwt.JwtFilter;
import com.training.rledenev.bankapp.security.jwt.JwtProvider;
import com.training.rledenev.bankapp.services.bot.AuthorizedUserService;
import com.training.rledenev.bankapp.services.bot.action.ActionMessageHandlerService;
import com.training.rledenev.bankapp.services.bot.action.impl.AgreementMessageHandlerService;
import com.training.rledenev.bankapp.services.bot.action.impl.CurrencyRatesHandlerService;
import com.training.rledenev.bankapp.services.bot.action.impl.ProductMessageHandlerService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.training.rledenev.bankapp.services.bot.impl.BotUtils.*;

@Service
public class AuthorizedUserServiceImpl implements AuthorizedUserService {
    public static final Map<Long, String> CHAT_ID_ACTION_NAME_MAP = new ConcurrentHashMap<>();
    static final Map<String, ActionMessageHandlerService> NAME_ACTION_MAP = new ConcurrentHashMap<>();
    private final AgreementMessageHandlerService agreementsMessageHandlerService;
    private final ProductMessageHandlerService productMessageHandlerService;
    private final CurrencyRatesHandlerService currencyRatesHandlerService;
    private final JwtProvider jwtProvider;
    private final JwtFilter jwtFilter;
    private final UserProvider userProvider;


    @PostConstruct
    private void init() {
        NAME_ACTION_MAP.put(NEW_AGREEMENTS, agreementsMessageHandlerService);
        NAME_ACTION_MAP.put(PRODUCTS, productMessageHandlerService);
        NAME_ACTION_MAP.put(CURRENCY_RATES, currencyRatesHandlerService);
    }

    public AuthorizedUserServiceImpl(AgreementMessageHandlerService agreementsMessageHandlerService,
                                     ProductMessageHandlerService productMessageHandlerService,
                                     CurrencyRatesHandlerService currencyRatesHandlerService, JwtProvider jwtProvider,
                                     JwtFilter jwtFilter, UserProvider userProvider) {
        this.agreementsMessageHandlerService = agreementsMessageHandlerService;
        this.productMessageHandlerService = productMessageHandlerService;
        this.currencyRatesHandlerService = currencyRatesHandlerService;
        this.jwtProvider = jwtProvider;
        this.jwtFilter = jwtFilter;
        this.userProvider = userProvider;
    }

    @Override
    public SendMessage handleRequests(String message, long chatId, String token) {
        if (jwtProvider.validateToken(token)) {
            jwtFilter.setCustomUserDetailsToSecurityContextHolder(token);
            Role userRole = userProvider.getCurrentUser().getRole();
            if (message.equals(BACK)) {
                CHAT_ID_ACTION_NAME_MAP.remove(chatId);
                ProductMessageHandlerService.CHAT_ID_AGREEMENT_DTO_MAP.remove(chatId);
                AgreementMessageHandlerService.CHAT_ID_AGREEMENT_DTO_MAP.remove(chatId);
                return createSendMessageWithButtons(chatId, SELECT_ACTION, getListOfActionsByUserRole(userRole));
            }
            String actionName = CHAT_ID_ACTION_NAME_MAP.get(chatId);
            if (actionName != null) {
                return NAME_ACTION_MAP.get(actionName).handleMessage(chatId, message, userRole);
            } else if (NAME_ACTION_MAP.containsKey(message)) {
                CHAT_ID_ACTION_NAME_MAP.put(chatId, message);
                return NAME_ACTION_MAP.get(message).handleMessage(chatId, message, userRole);
            }
            return createSendMessageWithButtons(chatId, UNKNOWN_INPUT_MESSAGE, getListOfActionsByUserRole(userRole));
        } else {
            UpdateHandlerServiceImpl.CHAT_ID_TOKEN_MAP.remove(chatId);
            return createSendMessageWithButtons(chatId, SESSION_CLOSED, List.of(REGISTER_USER, LOG_IN));
        }
    }
}
