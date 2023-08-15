package com.training.rledenev.bankapp.repository.mysql;

import com.training.rledenev.bankapp.entity.Manager;
import com.training.rledenev.bankapp.repository.ManagerRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ManagerRepositoryMySql extends CrudRepositoryMySQL<Manager> implements ManagerRepository {
    public ManagerRepositoryMySql() {
        setClazz(Manager.class);
    }
}
