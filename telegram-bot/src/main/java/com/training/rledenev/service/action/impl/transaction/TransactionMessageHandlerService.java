package com.training.rledenev.service.action.impl.transaction;

import com.training.rledenev.dto.AccountDto;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface TransactionMessageHandlerService {
    SendMessage handleMessage(long chatId, String message, AccountDto accountDto);
}
