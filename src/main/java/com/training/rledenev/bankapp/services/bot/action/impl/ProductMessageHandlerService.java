package com.training.rledenev.bankapp.services.bot.action.impl;

import com.training.rledenev.bankapp.dto.AgreementDto;
import com.training.rledenev.bankapp.dto.ProductDto;
import com.training.rledenev.bankapp.entity.enums.ProductType;
import com.training.rledenev.bankapp.entity.enums.Role;
import com.training.rledenev.bankapp.exceptions.ProductNotFoundException;
import com.training.rledenev.bankapp.services.AgreementService;
import com.training.rledenev.bankapp.services.ProductService;
import com.training.rledenev.bankapp.services.bot.action.ActionMessageHandlerService;
import com.training.rledenev.bankapp.services.bot.impl.AuthorizedUserServiceImpl;
import com.training.rledenev.bankapp.services.bot.impl.BotUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static com.training.rledenev.bankapp.services.bot.impl.BotUtils.*;

@Service
public class ProductMessageHandlerService implements ActionMessageHandlerService {
    public static final Map<Long, AgreementDto> CHAT_ID_AGREEMENT_DTO_MAP = new ConcurrentHashMap<>();
    private final AgreementService agreementService;
    private final ProductService productService;

    public ProductMessageHandlerService(AgreementService agreementService, ProductService productService) {
        this.agreementService = agreementService;
        this.productService = productService;
    }

    @Override
    public SendMessage handleMessage(long chatId, String message, Role role) {
        if (CHAT_ID_AGREEMENT_DTO_MAP.get(chatId) == null) {
            List<ProductDto> productDtos = productService.getAllActiveProductDtos();
            List<String> productTypes = productDtos.stream()
                    .map(ProductDto::getType)
                    .distinct()
                    .collect(Collectors.toList());
            if (message.equals(PRODUCTS)) {
                StringBuilder stringBuilder = new StringBuilder(PRODUCTS_LIST_MESSAGE);
                productTypes.forEach(productType -> stringBuilder.append(productType).append("\n"));
                stringBuilder.append("\n").append(SELECT_PRODUCT);
                productTypes.add(BACK);
                return createSendMessageWithButtons(chatId, stringBuilder.toString(), productTypes);
            }
            if (productTypes.contains(message)) {
                AgreementDto agreementDto = new AgreementDto();
                agreementDto.setProductType(message);
                CHAT_ID_AGREEMENT_DTO_MAP.put(chatId, agreementDto);
                return createSendMessageWithButtons(chatId,
                        getAllProductsWithTypeListMessage(message, productService.getActiveProductsWithType(message)),
                        getCurrencyButtons());
            }
            return createSendMessageWithButtons(chatId, UNKNOWN_INPUT_MESSAGE, List.of(EXIT));
        } else {
            AgreementDto agreementDto = CHAT_ID_AGREEMENT_DTO_MAP.get(chatId);
            if (agreementDto.getCurrencyCode() == null) {
                agreementDto.setCurrencyCode(message);
                if (agreementDto.getProductType().equals(ProductType.DEBIT_CARD.getName())
                        || agreementDto.getProductType().equals(ProductType.CREDIT_CARD.getName())) {
                    ProductDto productDto = productService.getSuitableProduct(agreementDto);
                    BigDecimal amount = BigDecimal.valueOf(productDto.getMinLimit())
                            .divide(productService.getRateOfCurrency(agreementDto.getCurrencyCode()), 2,
                                    RoundingMode.HALF_UP);
                    agreementDto.setPeriodMonths(productDto.getPeriodMonths());
                    agreementDto.setSum(amount.doubleValue());
                    agreementDto.setProductName(productDto.getName());
                    return createSendMessageWithButtons(chatId, String.format(SUITABLE_PRODUCT,
                            productDto.getName(), productDto.getInterestRate()), List.of(CONFIRM, BACK));
                } else {
                    return createSendMessage(chatId, ENTER_AMOUNT);
                }
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
                    ProductDto productDto = productService.getSuitableProduct(agreementDto);
                    agreementDto.setProductName(productDto.getName());
                    return createSendMessageWithButtons(chatId, String.format(SUITABLE_PRODUCT,
                            productDto.getName(), productDto.getInterestRate()), List.of(CONFIRM, BACK));
                } catch (NumberFormatException e) {
                    return createSendMessage(chatId, INCORRECT_NUMBER_INT);
                } catch (ProductNotFoundException e) {
                    CHAT_ID_AGREEMENT_DTO_MAP.remove(chatId);
                    AuthorizedUserServiceImpl.CHAT_ID_ACTION_NAME_MAP.remove(chatId);
                    return BotUtils.createSendMessageWithButtons(chatId, e.getMessage() + "\n" + SELECT_ACTION,
                            getListOfActionsByUserRole(role));
                }
            } else {
                agreementDto = agreementService.createNewAgreement(agreementDto);
                CHAT_ID_AGREEMENT_DTO_MAP.remove(chatId);
                AuthorizedUserServiceImpl.CHAT_ID_ACTION_NAME_MAP.remove(chatId);
                return BotUtils.createSendMessageWithButtons(chatId, getNewAgreementMessage(agreementDto),
                        getListOfActionsByUserRole(role));
            }
        }
    }

    private String getNewAgreementMessage(AgreementDto agreementDto) {
        return String.format(AGREEMENT_DONE, agreementDto.getProductName(), Math.round(agreementDto.getSum()),
                agreementDto.getCurrencyCode(), agreementDto.getInterestRate(), agreementDto.getPeriodMonths());
    }

    private static String getAllProductsWithTypeListMessage(String productType, List<ProductDto> allProductsWithType) {
        StringBuilder stringBuilder = new StringBuilder(String.format(PRODUCTS_LIST_OF_TYPE, productType));
        for (int i = 0; i < allProductsWithType.size(); i++) {
            ProductDto productDto = allProductsWithType.get(i);
            stringBuilder.append(i + 1)
                    .append(String.format(PRODUCT_INFO, productDto.getName(), productDto.getMinLimit(),
                            productDto.getInterestRate(), productDto.getPeriodMonths()))
                    .append("\n")
                    .append("\n");
        }
        stringBuilder.append("\n")
                .append(SELECT_CURRENCY)
                .append("\n")
                .append(NOTE_ABOUT_CONVERT);
        return stringBuilder.toString();
    }
}
