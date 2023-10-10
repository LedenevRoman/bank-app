package com.training.rledenev.bankapp.services.bot.chatmaps;

import com.training.rledenev.bankapp.dto.TransactionDto;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ChatIdTransactionDtoMap {
    private static final Map<Long, TransactionDto> CHAT_ID_TRANSACTION_DTO_MAP = new ConcurrentHashMap<>();

    private ChatIdTransactionDtoMap() {
    }

    public static void put(Long chatId, TransactionDto transactionDto) {
        CHAT_ID_TRANSACTION_DTO_MAP.put(chatId, transactionDto);
    }

    public static TransactionDto get(Long chatId) {
        return CHAT_ID_TRANSACTION_DTO_MAP.get(chatId);
    }

    public static void remove(Long chatId) {
        CHAT_ID_TRANSACTION_DTO_MAP.remove(chatId);
    }
}
