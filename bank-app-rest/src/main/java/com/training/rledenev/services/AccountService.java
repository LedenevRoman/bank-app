package com.training.rledenev.services;

import com.training.rledenev.dto.AccountDto;
import com.training.rledenev.entity.Account;

import java.util.List;

public interface AccountService {

    boolean checkAccountNumberExists(String number);

    List<AccountDto> getAccountsForClient();

    Account getAccountByNumber(String accountNumber);

    Account getMainBankAccount();
}
