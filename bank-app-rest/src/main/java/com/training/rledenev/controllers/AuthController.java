package com.training.rledenev.controllers;

import com.training.rledenev.dto.UserDto;
import com.training.rledenev.entity.User;
import com.training.rledenev.entity.enums.Role;
import com.training.rledenev.security.jwt.JwtProvider;
import com.training.rledenev.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/auth")
public class AuthController {
    private final UserService userService;
    private final JwtProvider jwtProvider;

    @PostMapping
    public ResponseEntity<String> auth(@RequestBody UserDto userDto) {
        User user = userService.findByEmailAndPassword(userDto.getEmail(), userDto.getPassword());
        return ResponseEntity.ok().body(jwtProvider.generateToken(user.getEmail()));
    }

    @GetMapping
    public Role auth() {
        return userService.getAuthorizedUserRole();
    }
}
