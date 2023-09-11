package com.training.rledenev.bankapp.services.impl;

import com.training.rledenev.bankapp.dto.UserDto;
import com.training.rledenev.bankapp.entity.User;
import com.training.rledenev.bankapp.entity.enums.Role;
import com.training.rledenev.bankapp.entity.enums.Status;
import com.training.rledenev.bankapp.exceptions.AuthenticationException;
import com.training.rledenev.bankapp.mapper.UserMapper;
import com.training.rledenev.bankapp.provider.UserProvider;
import com.training.rledenev.bankapp.repository.UserRepository;
import com.training.rledenev.bankapp.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserProvider userProvider;
    private final UserMapper userMapper;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository,
                           UserProvider userProvider, UserMapper userMapper) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userProvider = userProvider;
        this.userMapper = userMapper;
    }

    @Transactional
    @Override
    public User saveNewClient(UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user = userMapper.mapToEntity(userDto);
        user.setRole(Role.CLIENT);
        user.setStatus(Status.ACTIVE);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
        return user;
    }

    @Transactional
    @Override
    public User findByEmailAndPassword(String email, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        throw new AuthenticationException("Email or password is not correct");
    }
}
