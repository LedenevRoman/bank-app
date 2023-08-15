package com.training.rledenev.bankapp.repository.mysql;

import com.training.rledenev.bankapp.entity.Client;
import com.training.rledenev.bankapp.repository.ClientRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ClientRepositoryMySQL extends CrudRepositoryMySQL<Client> implements ClientRepository {
    public ClientRepositoryMySQL() {
        setClazz(Client.class);
    }
}
