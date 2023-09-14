package com.training.rledenev.bankapp.services.bot.action.impl;

import com.training.rledenev.bankapp.dto.AgreementDto;
import com.training.rledenev.bankapp.entity.Agreement;
import com.training.rledenev.bankapp.entity.Product;
import com.training.rledenev.bankapp.exceptions.ProductNotFoundException;
import com.training.rledenev.bankapp.services.AgreementService;
import com.training.rledenev.bankapp.services.ProductService;
import com.training.rledenev.bankapp.services.bot.action.ActionMessageHandlerService;
import com.training.rledenev.bankapp.services.bot.impl.AuthorizedUserServiceImpl;
import com.training.rledenev.bankapp.services.bot.impl.BotUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

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
                List<Product> allProductsWithType = productService.getActiveProductsWithType(message);
                String response = getAllProductsWithTypeListMessage(message, allProductsWithType);
                SendMessage sendMessage = createSendMessage(chatId, response);
                return addButtonsToMessage(sendMessage, getCurrencyButtons());
            }
            return createSendMessage(chatId, UNKNOWN_INPUT_MESSAGE);
        } else {
            AgreementDto agreementDto = CHAT_ID_AGREEMENT_DTO_MAP.get(chatId);
            if (agreementDto.getCurrencyCode() == null) {
                agreementDto.setCurrencyCode(message);
                return createSendMessage(chatId, ENTER_AMOUNT);
            }
            if (agreementDto.getSum() == null) {
                try {
                    agreementDto.setSum(Double.parseDouble(message));
                    return createSendMessage(chatId, ENTER_MINIMAL_PERIOD);
                } catch (NumberFormatException e) {
                    return createSendMessage(chatId, INCORRECT_NUMBER);
                }
            }
            if (agreementDto.getPeriodMonths() == null) {
                try {
                    agreementDto.setPeriodMonths(Integer.parseInt(message));
                    Product product = productService.getSuitableProduct(agreementDto);
                    agreementDto.setProductId(product.getId());
                    SendMessage sendMessage = createSendMessage(chatId, String.format(SUITABLE_PRODUCT,
                            product.getName(), product.getInterestRate().doubleValue()));
                    return addButtonsToMessage(sendMessage, List.of(CONFIRM, BACK));
                } catch (NumberFormatException e) {
                    return createSendMessage(chatId, INCORRECT_NUMBER_INT);
                } catch (ProductNotFoundException e) {
                    CHAT_ID_AGREEMENT_DTO_MAP.remove(chatId);
                    SendMessage sendMessage = createSendMessage(chatId, e.getMessage() + "\n" + SELECT_ACTION);
                    return BotUtils.addButtonsToMessage(sendMessage, getListOfActions());
                }
            } else {
                Agreement agreement = agreementService.createNewAgreement(agreementDto);
                CHAT_ID_AGREEMENT_DTO_MAP.remove(chatId);
                AuthorizedUserServiceImpl.CHAT_ID_ACTION_NAME_MAP.remove(chatId);
                String response = getNewAgreementMessage(agreement);
                SendMessage sendMessage = createSendMessage(chatId, response);
                return BotUtils.addButtonsToMessage(sendMessage, getListOfActions());
            }
        }
    }

    private String getNewAgreementMessage(Agreement agreement) {
        return String.format(AGREEMENT_DONE, agreement.getProduct().getType().toString(), agreement.getSum().toString(),
                agreement.getAccount().getCurrencyCode(), agreement.getPeriodMonths());
    }

    private static String getAllProductsWithTypeListMessage(String message, List<Product> allProductsWithType) {
        StringBuilder stringBuilder = new StringBuilder(String.format(PRODUCTS_LIST_OF_TYPE, message));
        for (int i = 0; i < allProductsWithType.size(); i++) {
            Product product = allProductsWithType.get(i);
            stringBuilder.append(i + 1)
                    .append(String.format(PRODUCT_INFO, product.getName(), product.getMaxLimit(),
                            product.getInterestRate().doubleValue(), product.getPeriodMonths()))
                    .append("\n");
        }
        stringBuilder.append("\n")
                .append(SELECT_CURRENCY)
                .append("\n")
                .append(NOTE_ABOUT_CONVERT);
        return stringBuilder.toString();
    }
}
