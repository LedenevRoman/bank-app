package com.training.rledenev.bankapp.repository.mysql;

import com.training.rledenev.bankapp.entity.Transaction;
import com.training.rledenev.bankapp.repository.TransactionRepository;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionRepositoryMySQL extends CrudRepositoryMySQL<Transaction> implements TransactionRepository {
    public TransactionRepositoryMySQL() {
        setClazz(Transaction.class);
    }
}
