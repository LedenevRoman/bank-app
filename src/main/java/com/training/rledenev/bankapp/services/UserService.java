package com.training.rledenev.bankapp.services;

import com.training.rledenev.bankapp.dto.UserDto;
import com.training.rledenev.bankapp.entity.User;
import com.training.rledenev.bankapp.entity.enums.Role;

public interface UserService {
    User saveNewClient(UserDto userDto);

    User findByEmailAndPassword(String email, String password);

    Role getAuthorizedUserRole();
}
