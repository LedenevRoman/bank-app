package com.training.rledenev.bankapp.converters;

import com.training.rledenev.bankapp.dto.ManagerDto;
import com.training.rledenev.bankapp.entity.Manager;
import com.training.rledenev.bankapp.entity.enums.Status;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ManagerConverter {
    public Manager convertToEntity(ManagerDto managerDto) {
        Manager manager = new Manager();
        manager.setCreatedAt(LocalDateTime.now());
        return manager.setFirstName(managerDto.getFirstName())
                .setLastName(managerDto.getLastName())
                .setStatus(Status.THREE);
    }
}
