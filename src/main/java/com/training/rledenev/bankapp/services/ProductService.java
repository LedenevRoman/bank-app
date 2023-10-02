package com.training.rledenev.bankapp.services;

import com.training.rledenev.bankapp.dto.AgreementDto;
import com.training.rledenev.bankapp.dto.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> getAllActiveProductDtos();

    List<ProductDto> getActiveProductsWithType(String productType);

    ProductDto getSuitableProduct(AgreementDto agreementDto);
}
