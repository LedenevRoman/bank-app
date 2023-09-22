package com.training.rledenev.bankapp.services.bot.action.impl.transaction;

import com.training.rledenev.bankapp.dto.AccountDto;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface TransactionMessageHandlerService {
    SendMessage handleMessage(long chatId, String message, AccountDto accountDto);
}
