package com.training.rledenev.repository;

import com.training.rledenev.entity.Product;
import com.training.rledenev.entity.enums.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT * FROM products p " +
            "WHERE p.status = 'ACTIVE' AND p.type = :type AND p.min_limit <= :sum " +
            "ORDER BY p.min_limit DESC LIMIT 1", nativeQuery = true)
    Optional<Product> getProductByTypeSumAndPeriod(@Param("type") String type, @Param("sum") Double sum);

    @Query("select p from Product p where p.status = 'ACTIVE'")
    List<Product> findAllActiveProducts();

    @Query("select p from Product p where p.status = 'ACTIVE' and p.type = :type")
    List<Product> findAllActiveProductsWithType(@Param("type") ProductType type);

    @Query("select p from Product p where p.status = 'ACTIVE' and p.name = :productName")
    Optional<Product> findActiveProductByName(@Param("productName") String productName);

    @Query(value = "SELECT * FROM products p " +
            "WHERE p.status = 'ACTIVE' AND p.type = :type " +
            "ORDER BY p.min_limit DESC LIMIT 1", nativeQuery = true)
    Optional<Product> getCardProduct(@Param("type") String type);
}
