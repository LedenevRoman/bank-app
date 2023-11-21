package com.training.rledenev.repository;

import com.training.rledenev.entity.Account;
import com.training.rledenev.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("select count(a) > 0 from Account a where a.number = :number")
    boolean isAccountNumberExists(@Param("number") String number);

    @EntityGraph(value = "user-account-agreement-product-graph")
    @Query("select a from Account a where a.client = :client and a.status = 'ACTIVE'")
    List<Account> getAccountsOfClient(@Param("client") User client);

    @Query("select a from Account a where a.number = :number")
    Optional<Account> getByNumber(@Param("number") String number);
}
