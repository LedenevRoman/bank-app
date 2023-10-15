package com.training.rledenev.services.impl;

import com.training.rledenev.entity.enums.Role;
import com.training.rledenev.provider.UserProvider;
import com.training.rledenev.security.jwt.JwtFilter;
import com.training.rledenev.security.jwt.JwtProvider;
import com.training.rledenev.services.AuthorizedUserService;
import com.training.rledenev.services.chatmaps.ChatIdActionNameMap;
import com.training.rledenev.services.chatmaps.ChatIdSecurityTokenMap;
import com.training.rledenev.services.chatmaps.NameActionServiceMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

import static com.training.rledenev.services.util.BotUtils.*;

@RequiredArgsConstructor
@Service
public class AuthorizedUserServiceImpl implements AuthorizedUserService {
    private final JwtProvider jwtProvider;
    private final JwtFilter jwtFilter;
    private final UserProvider userProvider;

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
