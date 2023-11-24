package com.training.rledenev.service.impl;

import com.training.rledenev.dto.AccountDto;
import com.training.rledenev.entity.Account;
import com.training.rledenev.entity.User;
import com.training.rledenev.exception.AccountNotFoundException;
import com.training.rledenev.mapper.AccountMapper;
import com.training.rledenev.security.UserProvider;
import com.training.rledenev.repository.AccountRepository;
import com.training.rledenev.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {
    private static final String MAIN_BANK_ACCOUNT_NUMBER = "1111111111111111";
    private final AccountMapper accountMapper;
    private final AccountRepository accountRepository;
    private final UserProvider userProvider;

    @Transactional
    @Override
    public boolean isAccountNumberExists(String number) {
        return accountRepository.isAccountNumberExists(number);
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
