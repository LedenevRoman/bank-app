package com.training.rledenev.bankapp.services.bot.impl;

import com.training.rledenev.bankapp.entity.enums.Role;
import com.training.rledenev.bankapp.provider.UserProvider;
import com.training.rledenev.bankapp.security.jwt.JwtFilter;
import com.training.rledenev.bankapp.security.jwt.JwtProvider;
import com.training.rledenev.bankapp.services.bot.AuthorizedUserService;
import com.training.rledenev.bankapp.services.bot.chatmaps.*;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

import static com.training.rledenev.bankapp.services.bot.util.BotUtils.*;

@Service
public class AuthorizedUserServiceImpl implements AuthorizedUserService {
    private final JwtProvider jwtProvider;
    private final JwtFilter jwtFilter;
    private final UserProvider userProvider;

    public AuthorizedUserServiceImpl(JwtProvider jwtProvider, JwtFilter jwtFilter, UserProvider userProvider) {
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
                removeIdFromMaps(chatId);
                return createSendMessageWithButtons(chatId, SELECT_ACTION, getListOfActionsByUserRole(userRole));
            }
            String actionName = ChatIdActionNameMap.get(chatId);
            if (actionName != null) {
                return NameActionServiceMap.get(actionName).handleMessage(chatId, message, userRole);
            } else if (NameActionServiceMap.containsKey(message)) {
                ChatIdActionNameMap.put(chatId, message);
                return NameActionServiceMap.get(message).handleMessage(chatId, message, userRole);
            }
            return createSendMessageWithButtons(chatId, UNKNOWN_INPUT_MESSAGE, getListOfActionsByUserRole(userRole));
        } else {
            ChatIdSecurityTokenMap.remove(chatId);
            removeIdFromMaps(chatId);
            return createSendMessageWithButtons(chatId, SESSION_CLOSED, List.of(REGISTER_USER, LOG_IN));
        }
    }
}
