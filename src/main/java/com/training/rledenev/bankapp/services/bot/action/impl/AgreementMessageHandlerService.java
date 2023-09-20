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

import static com.training.rledenev.bankapp.services.bot.impl.BotUtils.*;

@Service
public class AgreementMessageHandlerService implements ActionMessageHandlerService {
    public static final Map<Long, AgreementDto> CHAT_ID_AGREEMENT_DTO_MAP = new ConcurrentHashMap<>();
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
        if (CHAT_ID_AGREEMENT_DTO_MAP.get(chatId) == null) {
            if (message.equals(NEW_AGREEMENTS)) {
                return createSendMessageWithButtons(chatId, getNewAgreementsMessage(agreementDtos),
                        getListOfAgreementsIdButtons(agreementDtos));
            }
            long agreementId;
            try {
                agreementId = Long.parseLong(message);
            } catch (NumberFormatException exception) {
                return createSendMessageWithButtons(chatId, INCORRECT_NUMBER_INT,
                        getListOfAgreementsIdButtons(agreementDtos));
            }
            Optional<AgreementDto> optionalAgreementDto = agreementDtos.stream()
                    .filter(a -> a.getId().equals(agreementId))
                    .findFirst();
            if (optionalAgreementDto.isPresent()) {
                AgreementDto agreementDto = optionalAgreementDto.get();
                CHAT_ID_AGREEMENT_DTO_MAP.put(chatId, agreementDto);
                return createSendMessageWithButtons(chatId, getSelectedAgreementMessage(agreementDto),
                        getApproveDeclineButtons());
            } else {
                return createSendMessageWithButtons(chatId, WRONG_AGREEMENT_ID,
                        getListOfAgreementsIdButtons(agreementDtos));
            }
        } else {
            AgreementDto agreementDto = CHAT_ID_AGREEMENT_DTO_MAP.get(chatId);
            agreementDtos.remove(agreementDto);
            CHAT_ID_AGREEMENT_DTO_MAP.remove(chatId);
            if (message.equals(CONFIRM)) {
                agreementService.confirmAgreementByManager(agreementDto);
                return createSendMessageWithButtons(chatId,
                        getCustomMessage(AGREEMENT_CONFIRMED, agreementDtos, agreementDto),
                        getListOfAgreementsIdButtons(agreementDtos));
            }
            if (message.equals(BLOCK)) {
                agreementService.blockAgreementByManager(agreementDto);
                return createSendMessageWithButtons(chatId,
                        getCustomMessage(AGREEMENT_BLOCKED, agreementDtos, agreementDto),
                        getListOfAgreementsIdButtons(agreementDtos));
            }
        }
        return createSendMessageWithButtons(chatId, UNKNOWN_INPUT_MESSAGE, List.of(EXIT));
    }

    private String getCustomMessage(String message, List<AgreementDto> agreementDtos, AgreementDto agreementDto) {
        return String.format(message + getNewAgreementsMessage(agreementDtos), agreementDto.getId());
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

    private String getNewAgreementsMessage(List<AgreementDto> agreementDtos) {
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
