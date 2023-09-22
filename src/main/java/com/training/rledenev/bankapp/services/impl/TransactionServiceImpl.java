package com.training.rledenev.bankapp.services.impl;

import com.training.rledenev.bankapp.dto.AccountDto;
import com.training.rledenev.bankapp.dto.TransactionDto;
import com.training.rledenev.bankapp.entity.Account;
import com.training.rledenev.bankapp.entity.Transaction;
import com.training.rledenev.bankapp.entity.enums.CurrencyCode;
import com.training.rledenev.bankapp.exceptions.InsufficientFundsException;
import com.training.rledenev.bankapp.mapper.TransactionMapper;
import com.training.rledenev.bankapp.repository.AccountRepository;
import com.training.rledenev.bankapp.repository.TransactionRepository;
import com.training.rledenev.bankapp.services.AccountService;
import com.training.rledenev.bankapp.services.ProductService;
import com.training.rledenev.bankapp.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionMapper transactionMapper;
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final ProductService productService;
    private final AccountRepository accountRepository;

    @Transactional
    @Override
    public List<TransactionDto> getAllTransactionsOfAccount(AccountDto accountDto) {
        String accountNumber = accountDto.getNumber();
        List<Transaction> transactions = transactionRepository.getAllTransactionsWithAccountNumber(accountNumber);
        return transactionMapper.mapToListDto(transactions);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public void createTransaction(TransactionDto transactionDto) {
        String debitAccountNumber = transactionDto.getDebitAccountNumber();
        String creditAccountNumber = transactionDto.getCreditAccountNumber();
        Account debitAccount = accountService.getAccountByNumber(debitAccountNumber);
        Account creditAccount = accountService.getAccountByNumber(creditAccountNumber);
        Transaction transaction = transactionMapper.mapToEntity(transactionDto);
        BigDecimal debitBalanceDifference = calculateBalanceDifference(transaction.getAmount(),
                transaction.getCurrencyCode(), debitAccount.getCurrencyCode());
        if (debitBalanceDifference.compareTo(debitAccount.getBalance()) > 0) {
            throw new InsufficientFundsException("Not enough money");
        }
        BigDecimal creditBalanceDifference = calculateBalanceDifference(transaction.getAmount(),
                transaction.getCurrencyCode(), creditAccount.getCurrencyCode());
        transaction.setDebitBalanceDifference(debitBalanceDifference);
        transaction.setCreditBalanceDifference(creditBalanceDifference);
        transaction.setCreatedAt(LocalDateTime.now());
        debitAccount.setBalance(debitAccount.getBalance().subtract(debitBalanceDifference));
        creditAccount.setBalance(creditAccount.getBalance().add(creditBalanceDifference));
        debitAccount.setUpdatedAt(LocalDateTime.now());
        creditAccount.setUpdatedAt(LocalDateTime.now());
        transaction.setDebitAccount(debitAccount);
        transaction.setCreditAccount(creditAccount);
        transactionRepository.save(transaction);
        accountRepository.save(debitAccount);
        accountRepository.save(creditAccount);
    }

    private BigDecimal calculateBalanceDifference(BigDecimal amount, CurrencyCode transactionCurrency,
                                                  CurrencyCode accountCurrency) {
        BigDecimal rateTransactionCurrency = productService.getRateOfCurrency(transactionCurrency.toString());
        BigDecimal rateAccountCurrency = productService.getRateOfCurrency(accountCurrency.toString());
        BigDecimal rateOfConversion = rateTransactionCurrency.divide(rateAccountCurrency, 4, RoundingMode.HALF_UP);
        return amount.multiply(rateOfConversion);
    }
}
