package com.training.rledenev.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface LogInUserService {
    SendMessage handleLogInRequests(long chatId, String messageText);
}
