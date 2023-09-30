package com.training.rledenev.bankapp.controllers;

import com.training.rledenev.bankapp.dto.UserDto;
import com.training.rledenev.bankapp.entity.User;
import com.training.rledenev.bankapp.entity.enums.Role;
import com.training.rledenev.bankapp.security.SecurityToken;
import com.training.rledenev.bankapp.security.jwt.JwtProvider;
import com.training.rledenev.bankapp.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    public AuthController(UserService userService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping
    public ResponseEntity<SecurityToken> auth(@Valid @RequestBody UserDto userDto) {
        User user = userService.findByEmailAndPassword(userDto.getEmail(), userDto.getPassword());
        String tokenString = jwtProvider.generateToken(user.getEmail());
        SecurityToken tokenEntity = new SecurityToken();
        tokenEntity.setToken(tokenString);
        return ResponseEntity.ok().body(tokenEntity);
    }

    @GetMapping
    public Role auth() {
        return userService.getAuthorizedUserRole();
    }
}
