package com.training.rledenev.bankapp.services.impl;

import com.training.rledenev.bankapp.dto.AccountDto;
import com.training.rledenev.bankapp.entity.Account;
import com.training.rledenev.bankapp.entity.User;
import com.training.rledenev.bankapp.exceptions.AccountNotFoundException;
import com.training.rledenev.bankapp.mapper.AccountMapper;
import com.training.rledenev.bankapp.provider.UserProvider;
import com.training.rledenev.bankapp.repository.AccountRepository;
import com.training.rledenev.bankapp.services.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    private static final String MAIN_BANK_ACCOUNT_NUMBER = "1111111111111111";
    private final AccountMapper accountMapper;
    private final AccountRepository accountRepository;
    private final UserProvider userProvider;

    public AccountServiceImpl(AccountMapper accountMapper, AccountRepository accountRepository,
                              UserProvider userProvider) {
        this.accountMapper = accountMapper;
        this.accountRepository = accountRepository;
        this.userProvider = userProvider;
    }

    @Transactional
    @Override
    public boolean checkAccountNumberExists(String number) {
        return accountRepository.checkAccountNumberExists(number);
    }

    @Transactional
    @Override
    public List<AccountDto> getAccountsForClient() {
        User client = userProvider.getCurrentUser();
        return accountMapper.mapToListDtos(accountRepository.getAccountsOfClient(client));
    }

    @Transactional
    @Override
    public Account getAccountByNumber(String accountNumber) {
        return accountRepository.getByNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account not found with number " + accountNumber));
    }

    @Transactional
    @Override
    public Account getMainBankAccount() {
        return getAccountByNumber(MAIN_BANK_ACCOUNT_NUMBER);
    }
}
