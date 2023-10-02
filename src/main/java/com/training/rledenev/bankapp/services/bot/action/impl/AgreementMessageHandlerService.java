package com.training.rledenev.bankapp.services.bot.action.impl;

import com.training.rledenev.bankapp.dto.AgreementDto;
import com.training.rledenev.bankapp.entity.enums.Role;
import com.training.rledenev.bankapp.services.AgreementService;
import com.training.rledenev.bankapp.services.bot.action.ActionMessageHandlerService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static com.training.rledenev.bankapp.services.bot.util.BotUtils.*;

@Service
public class AgreementMessageHandlerService implements ActionMessageHandlerService {
    public static final Map<Long, Long> CHAT_ID_AGREEMENT_ID_MAP = new ConcurrentHashMap<>();
    private final AgreementService agreementService;

    public AgreementMessageHandlerService(AgreementService agreementService) {
        this.agreementService = agreementService;
    }

    @Override
    public SendMessage handleMessage(long chatId, String message, Role role) {
        if (role == Role.CLIENT) {
            return createSendMessageWithButtons(chatId, ACCESS_DENIED, getListOfActionsByUserRole(role));
        }
        List<AgreementDto> agreementDtos = agreementService.getAgreementsForManager();
        final Long agreementId;
        if (CHAT_ID_AGREEMENT_ID_MAP.get(chatId) == null) {
            if (message.equals(NEW_AGREEMENTS)) {
                return createSendMessageWithButtons(chatId, getListNewAgreementsMessage(agreementDtos),
                        getListOfAgreementsIdButtons(agreementDtos));
            }
            try {
                agreementId = Long.parseLong(message);
            } catch (NumberFormatException exception) {
                return createSendMessageWithButtons(chatId, INCORRECT_NUMBER_INT,
                        getListOfAgreementsIdButtons(agreementDtos));
            }
            Optional<AgreementDto> optionalAgreementDto = getOptionalFromListById(agreementDtos, agreementId);
            if (optionalAgreementDto.isPresent()) {
                AgreementDto agreementDto = optionalAgreementDto.get();
                CHAT_ID_AGREEMENT_ID_MAP.put(chatId, agreementId);
                return createSendMessageWithButtons(chatId, getSelectedAgreementMessage(agreementDto),
                        getConfirmBlockButtons());
            } else {
                return createSendMessageWithButtons(chatId, WRONG_AGREEMENT_ID,
                        getListOfAgreementsIdButtons(agreementDtos));
            }
        } else {
            agreementId = CHAT_ID_AGREEMENT_ID_MAP.get(chatId);
            agreementDtos.removeIf(a -> a.getId().equals(agreementId));
            CHAT_ID_AGREEMENT_ID_MAP.remove(chatId);
            if (message.equals(CONFIRM)) {
                agreementService.confirmAgreementByManager(agreementId);
                return createSendMessageWithButtons(chatId,
                        getStatusMessageWithListAgreements(AGREEMENT_CONFIRMED, agreementDtos, agreementId),
                        getListOfAgreementsIdButtons(agreementDtos));
            }
            if (message.equals(BLOCK)) {
                agreementService.blockAgreementByManager(agreementId);
                return createSendMessageWithButtons(chatId,
                        getStatusMessageWithListAgreements(AGREEMENT_BLOCKED, agreementDtos, agreementId),
                        getListOfAgreementsIdButtons(agreementDtos));
            }
        }
        return createSendMessageWithButtons(chatId, UNKNOWN_INPUT_MESSAGE, List.of(EXIT));
    }

    private static Optional<AgreementDto> getOptionalFromListById(List<AgreementDto> agreementDtos, long agreementId) {
        return agreementDtos.stream()
                .filter(a -> a.getId().equals(agreementId))
                .findFirst();
    }

    private String getStatusMessageWithListAgreements(String message, List<AgreementDto> agreementDtos,
                                                      Long agreementId) {
        return String.format(message + getListNewAgreementsMessage(agreementDtos), agreementId);
    }

    private String getSelectedAgreementMessage(AgreementDto agreementDto) {
        return String.format(SELECTED_AGREEMENT_INFO, agreementDto.getId(), agreementDto.getProductName(),
                agreementDto.getSum(), agreementDto.getCurrencyCode(), agreementDto.getPeriodMonths());
    }

    private List<String> getListOfAgreementsIdButtons(List<AgreementDto> agreementDtos) {
        List<String> buttons = agreementDtos.stream()
                .map(agreementDto -> agreementDto.getId().toString())
                .collect(Collectors.toList());
        buttons.add(BACK);
        return buttons;
    }

    private String getListNewAgreementsMessage(List<AgreementDto> agreementDtos) {
        StringBuilder stringBuilder = new StringBuilder(NEW_AGREEMENTS_LIST);
        for (AgreementDto agreementDto : agreementDtos) {
            stringBuilder.append(String.format(AGREEMENT_INFO, agreementDto.getId(), agreementDto.getProductName(),
                            agreementDto.getSum(), agreementDto.getCurrencyCode(), agreementDto.getPeriodMonths()))
                    .append("\n")
                    .append("\n");
        }
        stringBuilder.append("\n")
                .append(SELECT_AGREEMENT_ID);
        return stringBuilder.toString();
    }
}
