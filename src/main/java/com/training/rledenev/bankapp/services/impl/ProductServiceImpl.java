package com.training.rledenev.bankapp.services.impl;

import com.training.rledenev.bankapp.entity.Product;
import com.training.rledenev.bankapp.entity.enums.ProductType;
import com.training.rledenev.bankapp.repository.ProductRepository;
import com.training.rledenev.bankapp.services.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.training.rledenev.bankapp.services.impl.ServiceUtils.getEnumName;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    @Override
    public List<Product> getAllActiveProducts() {
        return productRepository.findAllActiveProducts();
    }

    @Transactional
    @Override
    public List<Product> getActiveProductsWithType(String productType) {
        return productRepository.findAllActiveProductsWithType(ProductType.valueOf(getEnumName(productType)));
    }
}
