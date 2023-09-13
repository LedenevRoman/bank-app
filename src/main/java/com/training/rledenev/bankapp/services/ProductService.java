package com.training.rledenev.bankapp.services;

import com.training.rledenev.bankapp.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllActiveProducts();

    List<Product> getActiveProductsWithType(String productType);
}
