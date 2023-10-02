package com.training.rledenev.bankapp.services;

import com.training.rledenev.bankapp.dto.AccountDto;
import com.training.rledenev.bankapp.entity.Account;

import java.util.List;

public interface AccountService {

    boolean checkAccountNumberExists(String number);

    List<AccountDto> getAccountsForClient();

    Account getAccountByNumber(String accountNumber);

    Account getMainBankAccount();
}
