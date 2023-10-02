package com.training.rledenev.bankapp.services.impl;

import com.training.rledenev.bankapp.dto.AgreementDto;
import com.training.rledenev.bankapp.dto.ProductDto;
import com.training.rledenev.bankapp.entity.Product;
import com.training.rledenev.bankapp.entity.enums.ProductType;
import com.training.rledenev.bankapp.exceptions.ProductNotFoundException;
import com.training.rledenev.bankapp.mapper.ProductMapper;
import com.training.rledenev.bankapp.repository.ProductRepository;
import com.training.rledenev.bankapp.services.CurrencyService;
import com.training.rledenev.bankapp.services.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CurrencyService currencyService;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper,
                              CurrencyService currencyService) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.currencyService = currencyService;
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
            BigDecimal rate = currencyService.getRateOfCurrency(agreementDto.getCurrencyCode());
            BigDecimal convertedAmount = amount.multiply(rate);
            product = productRepository.getProductByTypeSumAndPeriod(
                    getEnumName(agreementDto.getProductType()),
                    convertedAmount.doubleValue(),
                    agreementDto.getPeriodMonths()
            ).orElseThrow(() -> new ProductNotFoundException("Amount or period is out of limit"));
        }
        return productMapper.mapToDto(product);
    }

    private static String getEnumName(String string) {
        return string.toUpperCase().replaceAll("\\s", "_");
    }
}
