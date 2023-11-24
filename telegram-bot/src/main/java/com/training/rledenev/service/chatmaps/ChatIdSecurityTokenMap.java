package com.training.rledenev.service.chatmaps;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ChatIdSecurityTokenMap {
    static final Map<Long, String> CHAT_ID_TOKEN_MAP = new ConcurrentHashMap<>();

    private ChatIdSecurityTokenMap() {
    }

    public static void put(Long chatId, String securityToken) {
        CHAT_ID_TOKEN_MAP.put(chatId, securityToken);
    }

    public static String get(Long chatId) {
        return CHAT_ID_TOKEN_MAP.get(chatId);
    }

    public static void remove(Long chatId) {
        CHAT_ID_TOKEN_MAP.remove(chatId);
    }
}
