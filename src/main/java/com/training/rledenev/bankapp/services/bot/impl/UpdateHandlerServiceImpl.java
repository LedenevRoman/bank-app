package com.training.rledenev.bankapp.services.bot.impl;

import com.training.rledenev.bankapp.services.bot.AuthorizedUserService;
import com.training.rledenev.bankapp.services.bot.LogInUserService;
import com.training.rledenev.bankapp.services.bot.RegistrationUserService;
import com.training.rledenev.bankapp.services.bot.UpdateHandlerService;
import com.training.rledenev.bankapp.services.bot.chatmaps.*;
import com.training.rledenev.bankapp.services.bot.util.BotUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static com.training.rledenev.bankapp.services.bot.util.BotUtils.*;

@Service
public class UpdateHandlerServiceImpl implements UpdateHandlerService {
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
                ChatIdSecurityTokenMap.remove(chatId);
                removeIdFromMaps(chatId);
            }
            if (Boolean.TRUE.equals(ChatIdInRegistrationMap.get(chatId))) {
                return registrationUserService.handleRegistrationRequests(chatId, messageText, update);
            }
            if (Boolean.TRUE.equals(ChatIdInLoginMap.get(chatId))) {
                return logInUserService.handleLogInRequests(chatId, messageText);
            }
            if (ChatIdSecurityTokenMap.get(chatId) != null) {
                return authorizedUserService.handleRequests(messageText, chatId, ChatIdSecurityTokenMap.get(chatId));
            } else {
                return handleNotAuthorizedRequests(messageText, chatId);
            }
        }
        return createSendMessageWithButtons(chatId, UNKNOWN_INPUT_MESSAGE, List.of(EXIT));
    }

    private SendMessage handleNotAuthorizedRequests(String messageText, long chatId) {
        if (messageText.equals(START) || messageText.equals(EXIT)) {
            return startCommandReceived(chatId);
        }
        if (messageText.equals(REGISTER_USER)) {
            ChatIdInRegistrationMap.put(chatId, true);
            return createSendMessage(chatId, ENTER_FIRST_NAME);
        }
        if (messageText.equals(LOG_IN)) {
            ChatIdInLoginMap.put(chatId, true);
            return createSendMessage(chatId, ENTER_EMAIL);
        }
        return createSendMessageWithButtons(chatId, UNKNOWN_INPUT_MESSAGE, List.of(EXIT));
    }

    private SendMessage startCommandReceived(Long chatId) {
        return BotUtils.createSendMessageWithButtons(chatId, WELCOME_MESSAGE, List.of(REGISTER_USER, LOG_IN));
    }

    private static boolean checkMessageExists(Update update) {
        return update.hasMessage() && update.getMessage().hasText();
    }
}
