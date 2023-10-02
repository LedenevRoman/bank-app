package com.training.rledenev.bankapp.services;

import com.training.rledenev.bankapp.dto.TransactionDto;
import com.training.rledenev.bankapp.entity.Account;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    List<TransactionDto> getAllTransactionsOfAccount(String accountNumber);

    void createTransaction(TransactionDto transactionDto);

    void giveCreditFundsToAccount(Account account, BigDecimal amount);
}
