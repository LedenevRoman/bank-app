package com.training.rledenev.services.action;

import com.training.rledenev.entity.enums.Role;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface ActionMessageHandlerService {
    SendMessage handleMessage(long chatId, String message, Role userRole);
}
