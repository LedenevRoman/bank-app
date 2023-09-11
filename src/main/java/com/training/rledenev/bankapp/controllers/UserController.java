package com.training.rledenev.bankapp.controllers;

import com.training.rledenev.bankapp.dto.UserDto;
import com.training.rledenev.bankapp.entity.User;
import com.training.rledenev.bankapp.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping(path = "/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/new-client")
    public ResponseEntity<Long> saveNewClient(@RequestBody UserDto userDto) {
        User user = userService.saveNewClient(userDto);
        return ResponseEntity.created(URI.create("/" + user.getId())).body(user.getId());
    }
}
