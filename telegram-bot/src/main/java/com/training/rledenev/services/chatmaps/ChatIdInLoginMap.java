package com.training.rledenev.services.chatmaps;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ChatIdInLoginMap {
    static final Map<Long, Boolean> CHAT_ID_IS_IN_LOGIN_MAP = new ConcurrentHashMap<>();

    private ChatIdInLoginMap() {
    }

    public static void put(Long chatId, boolean isInRegistration) {
        CHAT_ID_IS_IN_LOGIN_MAP.put(chatId, isInRegistration);
    }

    public static Boolean get(Long chatId) {
        return CHAT_ID_IS_IN_LOGIN_MAP.get(chatId);
    }

    public static void remove(Long chatId) {
        CHAT_ID_IS_IN_LOGIN_MAP.remove(chatId);
    }
}
