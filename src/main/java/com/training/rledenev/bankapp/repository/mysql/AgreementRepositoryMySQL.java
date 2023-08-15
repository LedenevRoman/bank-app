package com.training.rledenev.bankapp.repository.mysql;

import com.training.rledenev.bankapp.entity.Agreement;
import com.training.rledenev.bankapp.repository.AgreementRepository;
import org.springframework.stereotype.Repository;

@Repository
public class AgreementRepositoryMySQL extends CrudRepositoryMySQL<Agreement> implements AgreementRepository {
    public AgreementRepositoryMySQL() {
        setClazz(Agreement.class);
    }
}
