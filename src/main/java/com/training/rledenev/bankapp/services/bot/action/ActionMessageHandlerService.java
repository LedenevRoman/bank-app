package com.training.rledenev.bankapp.services.bot.action;

import com.training.rledenev.bankapp.entity.enums.Role;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface ActionMessageHandlerService {
    SendMessage handleMessage(long chatId, String message, Role userRole);
}
