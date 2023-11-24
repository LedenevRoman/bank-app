package com.training.rledenev.service.chatmaps;

import com.training.rledenev.dto.AgreementDto;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ChatIdAgreementDtoMap {
    private static final Map<Long, AgreementDto> CHAT_ID_AGREEMENT_DTO_MAP = new ConcurrentHashMap<>();

    private ChatIdAgreementDtoMap() {
    }

    public static void put(Long chatId, AgreementDto agreementDto) {
        CHAT_ID_AGREEMENT_DTO_MAP.put(chatId, agreementDto);
    }

    public static AgreementDto get(Long chatId) {
        return CHAT_ID_AGREEMENT_DTO_MAP.get(chatId);
    }

    public static void remove(Long chatId) {
        CHAT_ID_AGREEMENT_DTO_MAP.remove(chatId);
    }
}
