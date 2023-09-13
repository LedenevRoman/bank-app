package com.training.rledenev.bankapp.repository;

import com.training.rledenev.bankapp.entity.Product;
import com.training.rledenev.bankapp.entity.enums.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value =
            "SELECT * FROM products p " +
            "WHERE p.type = :type AND p.max_limit > :sum " +
            "ORDER BY p.max_limit LIMIT 1", nativeQuery = true)
    Optional<Product> getProductByTypeAndSum(@Param("type") String type, @Param("sum") Double sum);

    @Query("select p from Product p where p.status = 'ACTIVE'")
    List<Product> findAllActiveProducts();

    @Query("select p from Product p where p.status = 'ACTIVE' and p.type = :type")
    List<Product> findAllActiveProductsWithType(@Param("type") ProductType type);
}
