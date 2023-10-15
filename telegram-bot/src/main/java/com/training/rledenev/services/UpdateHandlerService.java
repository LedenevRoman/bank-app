package com.training.rledenev.services;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface UpdateHandlerService {
    SendMessage handleUpdate(Update update);
}
