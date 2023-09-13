package com.training.rledenev.bankapp.services.bot.action.impl;

import com.training.rledenev.bankapp.dto.AgreementDto;
import com.training.rledenev.bankapp.entity.Agreement;
import com.training.rledenev.bankapp.entity.Product;
import com.training.rledenev.bankapp.entity.enums.CurrencyCode;
import com.training.rledenev.bankapp.exceptions.ProductNotFoundException;
import com.training.rledenev.bankapp.services.AgreementService;
import com.training.rledenev.bankapp.services.ProductService;
import com.training.rledenev.bankapp.services.bot.action.ActionMessageHandlerService;
import com.training.rledenev.bankapp.services.bot.impl.AuthorizedUserServiceImpl;
import com.training.rledenev.bankapp.services.bot.impl.BotUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.training.rledenev.bankapp.services.bot.impl.BotUtils.*;

@Service
public class ProductMessageHandlerService implements ActionMessageHandlerService {
    public static final Map<Long, AgreementDto> CHAT_ID_AGREEMENT_DTO_MAP = new HashMap<>();
    private final AgreementService agreementService;
    private final ProductService productService;

    public ProductMessageHandlerService(AgreementService agreementService, ProductService productService) {
        this.agreementService = agreementService;
        this.productService = productService;
    }

    @Override
    public SendMessage handleMessage(long chatId, String message) {
        if (CHAT_ID_AGREEMENT_DTO_MAP.get(chatId) == null) {
            List<Product> products = productService.getAllActiveProducts();
            List<String> productTypes = products.stream()
                    .map(product -> product.getType().toString())
                    .distinct()
                    .collect(Collectors.toList());
            if (message.equals(PRODUCTS)) {
                StringBuilder stringBuilder = new StringBuilder(PRODUCTS_LIST_MESSAGE);
                productTypes.forEach(productType -> stringBuilder.append(productType).append("\n"));
                stringBuilder.append("\n").append(SELECT_PRODUCT);
                productTypes.add(BACK);
                SendMessage sendMessage = createSendMessage(chatId, stringBuilder.toString());
                return addButtonsToMessage(sendMessage, productTypes);
            }
            if (productTypes.contains(message)) {
                AgreementDto agreementDto = new AgreementDto();
                agreementDto.setProductType(message);
                CHAT_ID_AGREEMENT_DTO_MAP.put(chatId, agreementDto);
                List<String> currenciesButtons = Arrays.stream(CurrencyCode.values())
                        .map(Enum::toString)
                        .collect(Collectors.toList());
                List<Product> allProductsWithType = productService.getActiveProductsWithType(message);
                String response = getAllProductsWithTypeListMessage(message, allProductsWithType);
                SendMessage sendMessage = createSendMessage(chatId, response);
                currenciesButtons.add(BACK);
                return addButtonsToMessage(sendMessage, currenciesButtons);
            }
            return createSendMessage(chatId, UNKNOWN_INPUT_MESSAGE);
        } else {
            AgreementDto agreementDto = CHAT_ID_AGREEMENT_DTO_MAP.get(chatId);
            if (agreementDto.getCurrencyCode() == null) {
                agreementDto.setCurrencyCode(message);
                return createSendMessage(chatId, ENTER_AMOUNT);
            } else {
                try {
                    agreementDto.setSum(Double.parseDouble(message));
                    Agreement agreement = agreementService.createNewAgreement(agreementDto);
                    CHAT_ID_AGREEMENT_DTO_MAP.remove(chatId);
                    AuthorizedUserServiceImpl.CHAT_ID_ACTION_NAME_MAP.remove(chatId);
                    String response = getNewAgreementMessage(agreement);
                    SendMessage sendMessage = createSendMessage(chatId, response);
                    return BotUtils.addButtonsToMessage(sendMessage, getListOfActions());
                } catch (NumberFormatException e) {
                    return createSendMessage(chatId, INCORRECT_NUMBER);
                } catch (ProductNotFoundException e) {
                    return createSendMessage(chatId, e.getMessage() + "\n" + ENTER_AMOUNT);
                }
            }
        }
    }

    private String getNewAgreementMessage(Agreement agreement) {
        return String.format(AGREEMENT_DONE, agreement.getProduct().getType().toString(), agreement.getSum().toString(),
                agreement.getAccount().getCurrencyCode(), agreement.getProduct().getPeriodMonths());
    }

    private static String getAllProductsWithTypeListMessage(String message, List<Product> allProductsWithType) {
        StringBuilder stringBuilder = new StringBuilder(String.format(PRODUCTS_LIST_OF_TYPE, message));
        for (int i = 0; i < allProductsWithType.size(); i++) {
            Product product = allProductsWithType.get(i);
            stringBuilder.append(i + 1)
                    .append(". ")
                    .append(String.format(PRODUCT_INFO, product.getMaxLimit(), product.getInterestRate().doubleValue(),
                            product.getPeriodMonths()))
                    .append("\n");
        }
        stringBuilder.append("\n")
                .append(SELECT_CURRENCY)
                .append("\n")
                .append(NOTE_ABOUT_CONVERT);
        return stringBuilder.toString();
    }
}
