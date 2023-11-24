package com.training.rledenev.service;

import com.training.rledenev.dto.AgreementDto;
import com.training.rledenev.dto.ProductDto;
import com.training.rledenev.entity.enums.ProductType;

import java.util.List;

public interface ProductService {
    List<ProductDto> getAllActiveProductDtos();

    List<ProductDto> getActiveProductsWithType(ProductType productType);

    ProductDto getSuitableProduct(AgreementDto agreementDto);
}
