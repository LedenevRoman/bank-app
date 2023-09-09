package com.training.rledenev.bankapp.services.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface RegistrationUserService {
    SendMessage handleRegistrationRequests(long chatId, String messageText, Update update);
}
