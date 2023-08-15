package com.training.rledenev.bankapp.repository.mysql;

import com.training.rledenev.bankapp.entity.Product;
import com.training.rledenev.bankapp.repository.ProductRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepositoryMySql extends CrudRepositoryMySQL<Product> implements ProductRepository {
    public ProductRepositoryMySql() {
        setClazz(Product.class);
    }
}
