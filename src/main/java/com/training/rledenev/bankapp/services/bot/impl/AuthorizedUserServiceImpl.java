package com.training.rledenev.bankapp.services.bot.impl;

import com.training.rledenev.bankapp.security.jwt.JwtProvider;
import com.training.rledenev.bankapp.services.bot.AuthorizedUserService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static com.training.rledenev.bankapp.services.bot.impl.BotUtils.*;

@Service
public class AuthorizedUserServiceImpl implements AuthorizedUserService {
    private final JwtProvider jwtProvider;

    public AuthorizedUserServiceImpl(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public SendMessage handleRequests(String message, long chatId, String token) {
        if (jwtProvider.validateToken(token)) {
            return createSendMessage(chatId, "TODO");
        } else {
            UpdateHandlerServiceImpl.CHAT_ID_TOKEN_MAP.remove(chatId);
            return createSendMessage(chatId, SESSION_CLOSED);
        }
    }
}
