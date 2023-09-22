package com.training.rledenev.bankapp.services;

import com.training.rledenev.bankapp.dto.AccountDto;
import com.training.rledenev.bankapp.dto.TransactionDto;

import java.util.List;

public interface TransactionService {
    List<TransactionDto> getAllTransactionsOfAccount(AccountDto accountDto);

    void createTransaction(TransactionDto transactionDto);
}
