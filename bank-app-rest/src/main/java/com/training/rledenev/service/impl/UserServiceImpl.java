package com.training.rledenev.service.impl;

import com.training.rledenev.dto.UserDto;
import com.training.rledenev.entity.User;
import com.training.rledenev.entity.enums.Role;
import com.training.rledenev.entity.enums.Status;
import com.training.rledenev.exception.AuthenticationException;
import com.training.rledenev.exception.UserAlreadyExistsException;
import com.training.rledenev.exception.UserNotFoundException;
import com.training.rledenev.mapper.UserMapper;
import com.training.rledenev.repository.UserRepository;
import com.training.rledenev.security.UserProvider;
import com.training.rledenev.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserProvider userProvider;
    private final UserMapper userMapper;

    @Transactional
    @Override
    public User saveNewClient(UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user = userMapper.mapToEntity(userDto);
        user.setRole(Role.CLIENT);
        user.setStatus(Status.ACTIVE);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException exception) {
            throw new UserAlreadyExistsException(String.format("User with email %s already exists.", user.getEmail()));
        }
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

    @Override
    public Role getAuthorizedUserRole() {
        return userProvider.getCurrentUser().getRole();
    }

    @Transactional
    @Override
    public UserDto getUserDtoById(Long id) {
        return userMapper.mapToDto(userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id " + id)));
    }
}
