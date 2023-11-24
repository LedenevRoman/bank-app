package com.training.rledenev.service.chatmaps;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ChatIdActionNameMap {
    private static final Map<Long, String> CHAT_ID_ACTION_NAME_MAP = new ConcurrentHashMap<>();

    private ChatIdActionNameMap() {
    }

    public static void put(Long chatId, String actionName) {
        CHAT_ID_ACTION_NAME_MAP.put(chatId, actionName);
    }

    public static String get(Long chatId) {
        return CHAT_ID_ACTION_NAME_MAP.get(chatId);
    }

    public static void remove(Long chatId) {
        CHAT_ID_ACTION_NAME_MAP.remove(chatId);
    }
}
