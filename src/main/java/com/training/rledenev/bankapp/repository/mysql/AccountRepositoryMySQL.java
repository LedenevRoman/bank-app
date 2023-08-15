package com.training.rledenev.bankapp.repository.mysql;

import com.training.rledenev.bankapp.entity.Account;
import com.training.rledenev.bankapp.repository.AccountRepository;
import org.springframework.stereotype.Repository;

@Repository
public class AccountRepositoryMySQL extends CrudRepositoryMySQL<Account> implements AccountRepository {
    public AccountRepositoryMySQL() {
        setClazz(Account.class);
    }
}
