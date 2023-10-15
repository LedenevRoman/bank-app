package com.training.rledenev.services.impl;

import com.training.rledenev.dto.AgreementDto;
import com.training.rledenev.dto.ProductDto;
import com.training.rledenev.entity.Product;
import com.training.rledenev.entity.enums.ProductType;
import com.training.rledenev.exceptions.ProductNotFoundException;
import com.training.rledenev.mapper.ProductMapper;
import com.training.rledenev.repository.ProductRepository;
import com.training.rledenev.services.CurrencyService;
import com.training.rledenev.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CurrencyService currencyService;

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
            BigDecimal rate = currencyService.getRateOfCurrency(agreementDto.getCurrencyCode());
            BigDecimal convertedAmount = amount.multiply(rate);
            product = productRepository.getProductByTypeSumAndPeriod(
                    getEnumName(agreementDto.getProductType()),
                    convertedAmount.doubleValue()
            ).orElseThrow(() -> new ProductNotFoundException("Amount or period is out of limit"));
        }
        return productMapper.mapToDto(product);
    }

    private static String getEnumName(String string) {
        return string.toUpperCase().replaceAll("\\s", "_");
    }
}
