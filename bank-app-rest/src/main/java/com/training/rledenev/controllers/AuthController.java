package com.training.rledenev.controllers;

import com.training.rledenev.dto.UserDto;
import com.training.rledenev.entity.User;
import com.training.rledenev.entity.enums.Role;
import com.training.rledenev.security.SecurityToken;
import com.training.rledenev.security.jwt.JwtProvider;
import com.training.rledenev.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/auth")
public class AuthController {
    private final UserService userService;
    private final JwtProvider jwtProvider;

    @PostMapping
    public ResponseEntity<SecurityToken> auth(@Valid @RequestBody UserDto userDto) {
        User user = userService.findByEmailAndPassword(userDto.getEmail(), userDto.getPassword());
        String tokenString = jwtProvider.generateToken(user.getEmail());
        SecurityToken tokenEntity = new SecurityToken(tokenString);
        return ResponseEntity.ok().body(tokenEntity);
    }

    @GetMapping
    public Role auth() {
        return userService.getAuthorizedUserRole();
    }
}
