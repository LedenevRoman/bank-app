package com.training.rledenev.bankapp.services.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface AuthorizedUserService {
    SendMessage handleRequests(String message, long chatId, String token);
}
