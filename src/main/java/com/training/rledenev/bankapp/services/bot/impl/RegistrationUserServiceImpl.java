package com.training.rledenev.bankapp.services.bot.impl;

import com.training.rledenev.bankapp.services.bot.RegistrationUserService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class RegistrationUserServiceImpl implements RegistrationUserService {
    @Override
    public SendMessage handleRegistrationRequests(long chatId, String messageText, Update update) {
        return null;
    }
}
