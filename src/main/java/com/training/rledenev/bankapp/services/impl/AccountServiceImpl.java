package com.training.rledenev.bankapp.services.impl;

import com.training.rledenev.bankapp.dto.AccountDto;
import com.training.rledenev.bankapp.entity.Account;
import com.training.rledenev.bankapp.mapper.AccountMapper;
import com.training.rledenev.bankapp.repository.AccountRepository;
import com.training.rledenev.bankapp.services.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountMapper accountMapper;
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountMapper accountMapper, AccountRepository accountRepository) {
        this.accountMapper = accountMapper;
        this.accountRepository = accountRepository;
    }

    @Transactional
    @Override
    public Account createAccount(AccountDto accountDto) {
        Account account = accountMapper.mapToEntity(accountDto);
        account.setCreatedAt(LocalDateTime.now());
        accountRepository.save(account);
        return account;
    }
}
