package com.training.rledenev.bankapp.services;

import com.training.rledenev.bankapp.dto.AgreementDto;
import com.training.rledenev.bankapp.entity.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    List<Product> getAllActiveProducts();

    List<Product> getActiveProductsWithType(String productType);

    BigDecimal getRateOfCurrency(String currencyCode);

    Product getSuitableProduct(AgreementDto agreementDto);
}
