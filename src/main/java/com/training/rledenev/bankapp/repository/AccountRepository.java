package com.training.rledenev.bankapp.repository;

import com.training.rledenev.bankapp.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("select count(a) > 0 from Account a where a.number = :number")
    boolean checkAccountNumberExists(@Param("number") String number);
}
