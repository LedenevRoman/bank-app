package com.training.rledenev.bankapp.services.bot.impl;

import com.training.rledenev.bankapp.security.SecurityToken;
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
    private static final Map<Long, Boolean> CHAT_ID_IS_IN_REGISTRATION_MAP = new HashMap<>();
    private static final Map<Long, Boolean> CHAT_ID_IS_IN_LOGIN_MAP = new HashMap<>();
    private static final Map<Long, SecurityToken> CHAT_ID_TOKEN_MAP = new HashMap<>();
    private final RegistrationUserService registrationUserService;
    private final LogInUserService logInUserService;

    public UpdateHandlerServiceImpl(RegistrationUserService registrationUserService,
                                    LogInUserService logInUserService) {
        this.registrationUserService = registrationUserService;
        this.logInUserService = logInUserService;
    }

    @Override
    public SendMessage handleUpdate(Update update) {
        long chatId = update.getMessage().getChatId();
        if(checkMessageExists(update)){
            String messageText = update.getMessage().getText();
            if (Boolean.TRUE.equals(CHAT_ID_IS_IN_REGISTRATION_MAP.get(chatId))) {
                return registrationUserService.handleRegistrationRequests(chatId, messageText, update);
            }
            if (Boolean.TRUE.equals(CHAT_ID_IS_IN_LOGIN_MAP.get(chatId))) {
                return logInUserService.handleLogInRequests(chatId, messageText);
            }
            return handleNotAuthorizedRequests(messageText, chatId);
        }
        return createSendMessage(chatId, UNKNOWN_INPUT_MESSAGE);
    }

    private SendMessage handleNotAuthorizedRequests(String messageText, long chatId) {
        if (messageText.equals(START)) {
            return startCommandReceived(chatId);
        }
        if (messageText.equals(REGISTER_USER)) {
            CHAT_ID_IS_IN_REGISTRATION_MAP.put(chatId, true);
            return createSendMessage(chatId, ENTER_YOUR_EMAIL);
        }
        if (messageText.equals(LOG_IN)) {
            CHAT_ID_IS_IN_LOGIN_MAP.put(chatId, true);
            return createSendMessage(chatId, ENTER_YOUR_EMAIL);
        }
        return createSendMessage(chatId, UNKNOWN_INPUT_MESSAGE);
    }

    private static boolean checkMessageExists(Update update) {
        return update.hasMessage() && update.getMessage().hasText();
    }

    private SendMessage startCommandReceived(Long chatId) {
        String message = "Welcome to banking application!";
        SendMessage sendMessage = createSendMessage(chatId, message);
        return BotUtils.addButtonsToMessage(sendMessage, List.of(REGISTER_USER, LOG_IN));
    }
}
