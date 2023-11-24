package com.training.rledenev.service.chatmaps;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ChatIdAgreementIdMap {
    private static final Map<Long, Long> CHAT_ID_AGREEMENT_ID_MAP = new ConcurrentHashMap<>();

    private ChatIdAgreementIdMap() {
    }

    public static void put(Long chatId, Long agreementId) {
        CHAT_ID_AGREEMENT_ID_MAP.put(chatId, agreementId);
    }

    public static Long get(Long chatId) {
        return CHAT_ID_AGREEMENT_ID_MAP.get(chatId);
    }

    public static void remove(Long chatId) {
        CHAT_ID_AGREEMENT_ID_MAP.remove(chatId);
    }
}
