package com.training.rledenev.bankapp.services.bot.action;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface ActionMessageHandlerService {
    SendMessage handleMessage(long chatId, String message);
}
