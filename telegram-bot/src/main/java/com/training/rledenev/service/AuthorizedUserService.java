package com.training.rledenev.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface AuthorizedUserService {
    SendMessage handleRequests(String message, long chatId, String token);
}
