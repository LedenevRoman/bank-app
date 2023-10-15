package com.training.rledenev.services;

import com.training.rledenev.dto.AgreementDto;
import com.training.rledenev.dto.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> getAllActiveProductDtos();

    List<ProductDto> getActiveProductsWithType(String productType);

    ProductDto getSuitableProduct(AgreementDto agreementDto);
}
