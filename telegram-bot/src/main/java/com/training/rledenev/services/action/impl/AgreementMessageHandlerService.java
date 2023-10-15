package com.training.rledenev.services.action.impl;

import com.training.rledenev.dto.AgreementDto;
import com.training.rledenev.entity.enums.Role;
import com.training.rledenev.services.AgreementService;
import com.training.rledenev.services.action.ActionMessageHandlerService;
import com.training.rledenev.services.chatmaps.ChatIdAgreementIdMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.training.rledenev.services.util.BotUtils.*;

@RequiredArgsConstructor
@Service
public class AgreementMessageHandlerService implements ActionMessageHandlerService {
    private final AgreementService agreementService;

    @Override
    public SendMessage handleMessage(long chatId, String message, Role role) {
        if (role == Role.CLIENT) {
            return createSendMessageWithButtons(chatId, ACCESS_DENIED, getListOfActionsByUserRole(role));
        }
        List<AgreementDto> agreementDtos = agreementService.getAgreementsForManager();
        final Long agreementId;
        if (ChatIdAgreementIdMap.get(chatId) == null) {
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
                ChatIdAgreementIdMap.put(chatId, agreementId);
                return createSendMessageWithButtons(chatId, getSelectedAgreementMessage(agreementDto),
                        getConfirmBlockButtons());
            } else {
                return createSendMessageWithButtons(chatId, WRONG_AGREEMENT_ID,
                        getListOfAgreementsIdButtons(agreementDtos));
            }
        } else {
            agreementId = ChatIdAgreementIdMap.get(chatId);
            agreementDtos.removeIf(a -> a.getId().equals(agreementId));
            ChatIdAgreementIdMap.remove(chatId);
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
                agreementDto.getSum(), agreementDto.getCurrencyCode(),
                getStringFormattedPeriod(agreementDto.getPeriodMonths()));
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
                            agreementDto.getSum(), agreementDto.getCurrencyCode(),
                            getStringFormattedPeriod(agreementDto.getPeriodMonths())))
                    .append("\n")
                    .append("\n");
        }
        stringBuilder.append("\n")
                .append(SELECT_AGREEMENT_ID);
        return stringBuilder.toString();
    }
}
