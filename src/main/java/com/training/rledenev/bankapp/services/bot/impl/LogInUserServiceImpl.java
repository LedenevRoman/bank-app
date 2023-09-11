package com.training.rledenev.bankapp.services.bot.impl;

import com.training.rledenev.bankapp.entity.User;
import com.training.rledenev.bankapp.exceptions.AuthenticationException;
import com.training.rledenev.bankapp.security.jwt.JwtProvider;
import com.training.rledenev.bankapp.services.UserService;
import com.training.rledenev.bankapp.services.bot.LogInUserService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.training.rledenev.bankapp.services.bot.impl.BotUtils.*;

@Service
public class LogInUserServiceImpl implements LogInUserService {
    private static final Map<Long, String> CHAT_ID_EMAIL_MAP = new HashMap<>();
    private final UserService userService;
    private final JwtProvider jwtProvider;

    public LogInUserServiceImpl(UserService userService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public SendMessage handleLogInRequests(long chatId, String messageText) {
        if (CHAT_ID_EMAIL_MAP.containsKey(chatId)) {
            User user;
            try {
                user = userService.findByEmailAndPassword(CHAT_ID_EMAIL_MAP.get(chatId), messageText);
                CHAT_ID_EMAIL_MAP.remove(chatId);
                UpdateHandlerServiceImpl.CHAT_ID_IS_IN_LOGIN_MAP.put(chatId, false);
            } catch (AuthenticationException e) {
                SendMessage sendMessage = createSendMessage(chatId, AUTHENTICATION_FAILED);
                return BotUtils.addButtonsToMessage(sendMessage, List.of(REGISTER_USER, LOG_IN));
            }
            String token = jwtProvider.generateToken(user.getEmail());
            UpdateHandlerServiceImpl.CHAT_ID_TOKEN_MAP.put(chatId, token);
            SendMessage sendMessage = createSendMessage(chatId, String.format(AUTHENTICATION_COMPLETED,
                    user.getFirstName(), user.getLastName()));
            return BotUtils.addButtonsToMessage(sendMessage, List.of(EXIT));
        } else {
            CHAT_ID_EMAIL_MAP.put(chatId, messageText);
            return createSendMessage(chatId, ENTER_PASSWORD);
        }
    }
}
