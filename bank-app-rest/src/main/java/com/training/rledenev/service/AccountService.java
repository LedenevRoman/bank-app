package com.training.rledenev.service;

import com.training.rledenev.dto.AccountDto;
import com.training.rledenev.entity.Account;

import java.util.List;

public interface AccountService {

    boolean isAccountNumberExists(String number);

    List<AccountDto> getAccountsForClient();

    Account getAccountByNumber(String accountNumber);

    Account getMainBankAccount();
}
