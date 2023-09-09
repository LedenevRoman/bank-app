package com.training.rledenev.bankapp.services.bot.impl;

import com.training.rledenev.bankapp.services.bot.LogInUserService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
public class LogInUserServiceImpl implements LogInUserService {
    @Override
    public SendMessage handleLogInRequests(long chatId, String messageText) {
        return null;
    }
}
