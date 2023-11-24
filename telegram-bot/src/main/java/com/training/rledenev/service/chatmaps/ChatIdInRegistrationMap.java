package com.training.rledenev.service.chatmaps;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ChatIdInRegistrationMap {
    private static final Map<Long, Boolean> CHAT_ID_IS_IN_REGISTRATION_MAP = new ConcurrentHashMap<>();

    private ChatIdInRegistrationMap() {
    }

    public static void put(Long chatId, boolean isInRegistration) {
        CHAT_ID_IS_IN_REGISTRATION_MAP.put(chatId, isInRegistration);
    }

    public static Boolean get(Long chatId) {
        return CHAT_ID_IS_IN_REGISTRATION_MAP.get(chatId);
    }

    public static void remove(Long chatId) {
        CHAT_ID_IS_IN_REGISTRATION_MAP.remove(chatId);
    }
}
