package com.training.rledenev.services;

import com.training.rledenev.dto.UserDto;
import com.training.rledenev.entity.User;
import com.training.rledenev.entity.enums.Role;

public interface UserService {
    User saveNewClient(UserDto userDto);

    User findByEmailAndPassword(String email, String password);

    Role getAuthorizedUserRole();
}
