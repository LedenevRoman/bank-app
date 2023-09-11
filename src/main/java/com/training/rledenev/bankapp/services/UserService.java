package com.training.rledenev.bankapp.services;

import com.training.rledenev.bankapp.dto.UserDto;
import com.training.rledenev.bankapp.entity.User;

public interface UserService {
    User saveNewClient(UserDto userDto);

    User findByEmailAndPassword(String email, String password);
}
