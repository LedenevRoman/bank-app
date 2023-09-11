package com.training.rledenev.bankapp.services.bot.impl;

import com.training.rledenev.bankapp.services.bot.AuthorizedUserService;
import com.training.rledenev.bankapp.services.bot.LogInUserService;
import com.training.rledenev.bankapp.services.bot.RegistrationUserService;
import com.training.rledenev.bankapp.services.bot.UpdateHandlerService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.training.rledenev.bankapp.services.bot.impl.BotUtils.*;

@Service
public class UpdateHandlerServiceImpl implements UpdateHandlerService {
    static final Map<Long, String> CHAT_ID_TOKEN_MAP = new HashMap<>();
    static final Map<Long, Boolean> CHAT_ID_IS_IN_REGISTRATION_MAP = new HashMap<>();
    static final Map<Long, Boolean> CHAT_ID_IS_IN_LOGIN_MAP = new HashMap<>();
    private final RegistrationUserService registrationUserService;
    private final LogInUserService logInUserService;
    private final AuthorizedUserService authorizedUserService;

    public UpdateHandlerServiceImpl(RegistrationUserService registrationUserService,
                                    LogInUserService logInUserService, AuthorizedUserService authorizedUserService) {
        this.registrationUserService = registrationUserService;
        this.logInUserService = logInUserService;
        this.authorizedUserService = authorizedUserService;
    }

    @Override
    public SendMessage handleUpdate(Update update) {
        long chatId = update.getMessage().getChatId();
        if(checkMessageExists(update)){
            String messageText = update.getMessage().getText();
            if (messageText.equals(EXIT)) {
                CHAT_ID_TOKEN_MAP.remove(chatId);
            }
            if (Boolean.TRUE.equals(CHAT_ID_IS_IN_REGISTRATION_MAP.get(chatId))) {
                return registrationUserService.handleRegistrationRequests(chatId, messageText, update);
            }
            if (Boolean.TRUE.equals(CHAT_ID_IS_IN_LOGIN_MAP.get(chatId))) {
                return logInUserService.handleLogInRequests(chatId, messageText);
            }
            if (CHAT_ID_TOKEN_MAP.get(chatId) != null) {
                return authorizedUserService.handleRequests(messageText, chatId, CHAT_ID_TOKEN_MAP.get(chatId));
            } else {
                return handleNotAuthorizedRequests(messageText, chatId);
            }
        }
        return createSendMessage(chatId, UNKNOWN_INPUT_MESSAGE);
    }

    private SendMessage handleNotAuthorizedRequests(String messageText, long chatId) {
        if (messageText.equals(START) || messageText.equals(EXIT)) {
            return startCommandReceived(chatId);
        }
        if (messageText.equals(REGISTER_USER)) {
            CHAT_ID_IS_IN_REGISTRATION_MAP.put(chatId, true);
            return createSendMessage(chatId, ENTER_FIRST_NAME);
        }
        if (messageText.equals(LOG_IN)) {
            CHAT_ID_IS_IN_LOGIN_MAP.put(chatId, true);
            return createSendMessage(chatId, ENTER_EMAIL);
        }
        return createSendMessage(chatId, UNKNOWN_INPUT_MESSAGE);
    }

    private SendMessage startCommandReceived(Long chatId) {
        SendMessage sendMessage = createSendMessage(chatId, WELCOME_MESSAGE);
        return BotUtils.addButtonsToMessage(sendMessage, List.of(REGISTER_USER, LOG_IN));
    }

    private static boolean checkMessageExists(Update update) {
        return update.hasMessage() && update.getMessage().hasText();
    }
}
