package com.training.rledenev.bankapp.services.impl;

import com.training.rledenev.bankapp.converters.ManagerConverter;
import com.training.rledenev.bankapp.dto.ManagerDto;
import com.training.rledenev.bankapp.entity.Manager;
import com.training.rledenev.bankapp.repository.ManagerRepository;
import com.training.rledenev.bankapp.services.ManagerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ManagerServiceImpl implements ManagerService {
    private final ManagerConverter managerConverter;
    private final ManagerRepository managerRepository;

    public ManagerServiceImpl(ManagerConverter managerConverter, ManagerRepository managerRepository) {
        this.managerConverter = managerConverter;
        this.managerRepository = managerRepository;
    }

    @Transactional
    @Override
    public Manager createManager(ManagerDto managerDto) {
        Manager manager = managerConverter.convertToEntity(managerDto);
        managerRepository.save(manager);
        return manager;
    }
}
