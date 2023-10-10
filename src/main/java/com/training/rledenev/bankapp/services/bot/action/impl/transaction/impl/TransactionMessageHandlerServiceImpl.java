package com.training.rledenev.bankapp.services.bot.action.impl.transaction.impl;

import com.training.rledenev.bankapp.dto.AccountDto;
import com.training.rledenev.bankapp.dto.TransactionDto;
import com.training.rledenev.bankapp.exceptions.InsufficientFundsException;
import com.training.rledenev.bankapp.services.TransactionService;
import com.training.rledenev.bankapp.services.bot.action.impl.transaction.TransactionMessageHandlerService;
import com.training.rledenev.bankapp.services.bot.chatmaps.ChatIdTransactionDtoMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

import static com.training.rledenev.bankapp.services.bot.util.BotUtils.*;

@Service
@RequiredArgsConstructor
public class TransactionMessageHandlerServiceImpl implements TransactionMessageHandlerService {
    private final TransactionService transactionService;

    @Override
    public SendMessage handleMessage(long chatId, String message, AccountDto accountDto) {
        if (ChatIdTransactionDtoMap.get(chatId) == null) {
            return handleInitialTransactionMessage(chatId, accountDto);
        } else {
            return handleCreationTransactionMessage(chatId, message);
        }
    }

    private static SendMessage handleInitialTransactionMessage(long chatId, AccountDto accountDto) {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setDebitAccountNumber(accountDto.getNumber());
        ChatIdTransactionDtoMap.put(chatId, transactionDto);
        return createSendMessage(chatId, ENTER_ACCOUNT_NUMBER);
    }

    private SendMessage handleCreationTransactionMessage(long chatId, String message) {
        TransactionDto transactionDto = ChatIdTransactionDtoMap.get(chatId);
        SendMessage fillInMessage = fillInTransactionDtoMessage(chatId, message, transactionDto);
        if (fillInMessage != null) {
            return fillInMessage;
        }
        if (message.equals(CONFIRM)) {
            return createNewTransactionMessage(chatId, transactionDto);
        }
        if (message.equals(CANCEL)) {
            ChatIdTransactionDtoMap.remove(chatId);
            return createSendMessageWithButtons(chatId, TRANSACTION_CANCELED, List.of(BACK_TO_LIST_ACCOUNTS));
        } else {
            return createSendMessageWithButtons(chatId, UNKNOWN_INPUT_MESSAGE, List.of(EXIT));
        }
    }

    private SendMessage fillInTransactionDtoMessage(long chatId, String message, TransactionDto transactionDto) {
        if (transactionDto.getCreditAccountNumber() == null) {
            transactionDto.setCreditAccountNumber(message);
            return createSendMessageWithButtons(chatId, SELECT_CURRENCY, getCurrencyButtons());
        }
        if (transactionDto.getCurrencyCode() == null) {
            transactionDto.setCurrencyCode(message);
            return createSendMessage(chatId, ENTER_AMOUNT);
        }
        if (transactionDto.getAmount() == null) {
            try {
                transactionDto.setAmount(Double.parseDouble(message));
                return createSendMessageWithButtons(chatId, SELECT_TYPE, getTypeButtons());
            } catch (NumberFormatException e) {
                return createSendMessage(chatId, INCORRECT_NUMBER);
            }
        }
        if (transactionDto.getType() == null && getTypeButtons().contains(message)) {
            transactionDto.setType(message);
            return createSendMessage(chatId, ENTER_DESCRIPTION);
        }
        if (transactionDto.getDescription() == null) {
            transactionDto.setDescription(message);
            return createSendMessageWithButtons(chatId, getTransactionSummaryMessage(transactionDto),
                    List.of(CONFIRM, CANCEL));
        }
        return null;
    }

    private SendMessage createNewTransactionMessage(long chatId, TransactionDto transactionDto) {
        ChatIdTransactionDtoMap.remove(chatId);
        try {
            transactionService.createTransaction(transactionDto);
        } catch (InsufficientFundsException e) {
            return createSendMessageWithButtons(chatId, INSUFFICIENT_FUNDS, List.of(BACK_TO_LIST_ACCOUNTS));
        } catch (RuntimeException e) {
            return createSendMessageWithButtons(chatId, SOMETHING_WRONG, List.of(BACK_TO_LIST_ACCOUNTS));
        }
        return createSendMessageWithButtons(chatId, TRANSACTION_COMPLETED, List.of(BACK_TO_LIST_ACCOUNTS));
    }

    private String getTransactionSummaryMessage(TransactionDto transactionDto) {
        return String.format(TRANSACTION_INFO, transactionDto.getCreditAccountNumber(), transactionDto.getAmount(),
                transactionDto.getCurrencyCode(), transactionDto.getType(), transactionDto.getDescription());
    }
}
