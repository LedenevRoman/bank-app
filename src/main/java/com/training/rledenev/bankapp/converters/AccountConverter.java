package com.training.rledenev.bankapp.converters;

import com.training.rledenev.bankapp.dto.AccountDto;
import com.training.rledenev.bankapp.entity.Account;
import com.training.rledenev.bankapp.entity.enums.AccountType;
import com.training.rledenev.bankapp.entity.enums.CurrencyCode;
import com.training.rledenev.bankapp.entity.enums.Status;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class AccountConverter {
    public Account convertToEntity(AccountDto accountDto) {
        Account account = new Account();
        return account.setName(accountDto.getName())
                .setAccountType(AccountType.values()[accountDto.getAccountType()])
                .setStatus(Status.values()[accountDto.getStatus()])
                .setBalance(BigDecimal.valueOf(accountDto.getBalance()))
                .setCurrencyCode(CurrencyCode.values()[accountDto.getCurrencyCode()])
                .setCreatedAt(LocalDateTime.now());
    }
}
