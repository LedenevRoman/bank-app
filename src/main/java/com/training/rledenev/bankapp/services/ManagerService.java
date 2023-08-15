package com.training.rledenev.bankapp.services;

import com.training.rledenev.bankapp.dto.ManagerDto;
import com.training.rledenev.bankapp.entity.Manager;

public interface ManagerService {
    Manager createManager(ManagerDto managerDto);
}
