package com.training.rledenev.bankapp.services.bot.impl;

import com.training.rledenev.bankapp.entity.User;
import com.training.rledenev.bankapp.exceptions.AuthenticationException;
import com.training.rledenev.bankapp.security.jwt.JwtProvider;
import com.training.rledenev.bankapp.services.UserService;
import com.training.rledenev.bankapp.services.bot.LogInUserService;
import com.training.rledenev.bankapp.services.bot.chatmaps.ChatIdInLoginMap;
import com.training.rledenev.bankapp.services.bot.chatmaps.ChatIdSecurityTokenMap;
import com.training.rledenev.bankapp.services.bot.util.BotUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.training.rledenev.bankapp.services.bot.util.BotUtils.*;

@Service
public class LogInUserServiceImpl implements LogInUserService {
    private static final Map<Long, String> CHAT_ID_EMAIL_MAP = new ConcurrentHashMap<>();
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
            } catch (AuthenticationException e) {
                return BotUtils.createSendMessageWithButtons(chatId, AUTHENTICATION_FAILED, List.of(REGISTER_USER, LOG_IN));
            } finally {
                CHAT_ID_EMAIL_MAP.remove(chatId);
                ChatIdInLoginMap.put(chatId, false);
            }
            String token = jwtProvider.generateToken(user.getEmail());
            ChatIdSecurityTokenMap.put(chatId, token);
            return BotUtils.createSendMessageWithButtons(chatId, String.format(AUTHENTICATION_COMPLETED,
                    user.getFirstName(), user.getLastName()) + SELECT_ACTION, getListOfActionsByUserRole(user.getRole()));
        } else {
            CHAT_ID_EMAIL_MAP.put(chatId, messageText);
            return createSendMessage(chatId, ENTER_PASSWORD);
        }
    }
}
