package com.training.rledenev.bankapp.services;

import com.training.rledenev.bankapp.dto.AccountDto;
import com.training.rledenev.bankapp.entity.Account;

public interface AccountService {
    Account createAccount(AccountDto accountDto);

    boolean checkAccountNumberExists(String number);
}
