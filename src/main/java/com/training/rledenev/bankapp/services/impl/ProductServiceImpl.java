package com.training.rledenev.bankapp.services.impl;

import com.training.rledenev.bankapp.dto.AgreementDto;
import com.training.rledenev.bankapp.dto.ProductDto;
import com.training.rledenev.bankapp.entity.Product;
import com.training.rledenev.bankapp.entity.enums.CurrencyCode;
import com.training.rledenev.bankapp.entity.enums.ProductType;
import com.training.rledenev.bankapp.exceptions.ProductNotFoundException;
import com.training.rledenev.bankapp.exceptions.RequestApiException;
import com.training.rledenev.bankapp.mapper.ProductMapper;
import com.training.rledenev.bankapp.repository.ProductRepository;
import com.training.rledenev.bankapp.services.ProductService;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import static com.training.rledenev.bankapp.services.impl.ServiceUtils.getEnumName;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Transactional
    @Override
    public List<ProductDto> getAllActiveProductDtos() {
        return productMapper.mapToListDto(productRepository.findAllActiveProducts());
    }

    @Transactional
    @Override
    public List<ProductDto> getActiveProductsWithType(String productType) {
        return productMapper.mapToListDto(productRepository
                .findAllActiveProductsWithType(ProductType.valueOf(getEnumName(productType))));
    }

    @Transactional
    @Override
    public ProductDto getSuitableProduct(AgreementDto agreementDto) {
        Product product;
        if (agreementDto.getProductType().equals(ProductType.DEBIT_CARD.getName()) ||
                agreementDto.getProductType().equals(ProductType.CREDIT_CARD.getName())) {
            product = productRepository.getCardProduct(getEnumName(agreementDto.getProductType()))
                    .orElseThrow(() -> new ProductNotFoundException("No product type"));
        } else {
            BigDecimal amount = BigDecimal.valueOf(agreementDto.getSum());
            BigDecimal rate = getRateOfCurrency(agreementDto.getCurrencyCode());
            BigDecimal convertedAmount = amount.multiply(rate);
            product = productRepository.getProductByTypeSumAndPeriod(
                    getEnumName(agreementDto.getProductType()),
                    convertedAmount.doubleValue(),
                    agreementDto.getPeriodMonths()
            ).orElseThrow(() -> new ProductNotFoundException("Amount or period is out of limit"));
        }
        return productMapper.mapToDto(product);
    }

    @Override
    public BigDecimal getRateOfCurrency(String currencyCode) {
        if (currencyCode.equals(CurrencyCode.PLN.toString())) {
            return BigDecimal.valueOf(1);
        }
        JSONObject currencyJson;
        try {
            currencyJson = getCurrencyJsonObject(currencyCode);
        } catch (IOException e) {
            throw new RequestApiException(e.getMessage());
        }
        JSONObject subObject = currencyJson.getJSONArray("rates").getJSONObject(0);
        return BigDecimal.valueOf(subObject.getDouble("mid"));
    }

    @NotNull
    private static JSONObject getCurrencyJsonObject(String message) throws IOException {
        URL url = new URL("http://api.nbp.pl/api/exchangerates/rates/A/" + message);
        Scanner scanner = new Scanner((InputStream) url.getContent());
        StringBuilder result = new StringBuilder();
        while (scanner.hasNext()) {
            result.append(scanner.nextLine());
        }
        return new JSONObject(result.toString());
    }
}
