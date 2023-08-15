package com.training.rledenev.bankapp.services.impl;

import com.training.rledenev.bankapp.converters.AccountConverter;
import com.training.rledenev.bankapp.dto.AccountDto;
import com.training.rledenev.bankapp.entity.Account;
import com.training.rledenev.bankapp.repository.AccountRepository;
import com.training.rledenev.bankapp.services.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountConverter accountConverter;
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountConverter accountConverter, AccountRepository accountRepository) {
        this.accountConverter = accountConverter;
        this.accountRepository = accountRepository;
    }

    @Transactional
    @Override
    public Account createAccount(AccountDto accountDto) {
        Account account = accountConverter.convertToEntity(accountDto);
        accountRepository.save(account);
        return account;
    }
}
