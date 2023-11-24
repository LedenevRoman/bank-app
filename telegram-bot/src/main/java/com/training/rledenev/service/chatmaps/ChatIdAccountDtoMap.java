package com.training.rledenev.service.chatmaps;

import com.training.rledenev.dto.AccountDto;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ChatIdAccountDtoMap {
    private static final Map<Long, AccountDto> CHAT_ID_ACCOUNT_DTO_MAP = new ConcurrentHashMap<>();

    private ChatIdAccountDtoMap() {
    }

    public static void put(Long chatId, AccountDto accountDto) {
        CHAT_ID_ACCOUNT_DTO_MAP.put(chatId, accountDto);
    }

    public static AccountDto get(Long chatId) {
        return CHAT_ID_ACCOUNT_DTO_MAP.get(chatId);
    }

    public static void remove(Long chatId) {
        CHAT_ID_ACCOUNT_DTO_MAP.remove(chatId);
    }
}
