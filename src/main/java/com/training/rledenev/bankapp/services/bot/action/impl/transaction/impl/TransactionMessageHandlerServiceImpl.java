package com.training.rledenev.bankapp.services.bot.action.impl.transaction.impl;

import com.training.rledenev.bankapp.dto.AccountDto;
import com.training.rledenev.bankapp.dto.TransactionDto;
import com.training.rledenev.bankapp.exceptions.InsufficientFundsException;
import com.training.rledenev.bankapp.services.TransactionService;
import com.training.rledenev.bankapp.services.bot.action.impl.transaction.TransactionMessageHandlerService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.training.rledenev.bankapp.services.bot.impl.BotUtils.*;

@Service
@RequiredArgsConstructor
public class TransactionMessageHandlerServiceImpl implements TransactionMessageHandlerService {
    public static final Map<Long, TransactionDto> CHAT_ID_TRANSACTION_DTO_MAP = new ConcurrentHashMap<>();
    private final TransactionService transactionService;

    @Override
    public SendMessage handleMessage(long chatId, String message, AccountDto accountDto) {
        if (message.equals(MAKE_TRANSACTION)) {
            TransactionDto transactionDto = new TransactionDto();
            transactionDto.setDebitAccountNumber(accountDto.getNumber());
            CHAT_ID_TRANSACTION_DTO_MAP.put(chatId, transactionDto);
            return createSendMessage(chatId, ENTER_ACCOUNT_NUMBER);
        }
        if (CHAT_ID_TRANSACTION_DTO_MAP.get(chatId) != null) {
            TransactionDto transactionDto = CHAT_ID_TRANSACTION_DTO_MAP.get(chatId);
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
            if (message.equals(CONFIRM)) {
                CHAT_ID_TRANSACTION_DTO_MAP.remove(chatId);
                try {
                    transactionService.createTransaction(transactionDto);
                } catch (InsufficientFundsException e) {
                    return createSendMessageWithButtons(chatId, INSUFFICIENT_FUNDS, List.of(BACK_TO_LIST_ACCOUNTS));
                } catch (RuntimeException e) {
                    return createSendMessageWithButtons(chatId, SOMETHING_WRONG, List.of(BACK_TO_LIST_ACCOUNTS));
                }
                return createSendMessageWithButtons(chatId, TRANSACTION_COMPLETED, List.of(BACK_TO_LIST_ACCOUNTS));
            }
            if (message.equals(CANCEL)) {
                CHAT_ID_TRANSACTION_DTO_MAP.remove(chatId);
                return createSendMessageWithButtons(chatId, TRANSACTION_CANCELED, List.of(BACK_TO_LIST_ACCOUNTS));
            }
        }
        return createSendMessageWithButtons(chatId, UNKNOWN_INPUT_MESSAGE, List.of(EXIT));
    }

    private String getTransactionSummaryMessage(TransactionDto transactionDto) {
        return String.format(TRANSACTION_INFO, transactionDto.getCreditAccountNumber(), transactionDto.getAmount(),
                transactionDto.getCurrencyCode(), transactionDto.getType(), transactionDto.getDescription());
    }
}
