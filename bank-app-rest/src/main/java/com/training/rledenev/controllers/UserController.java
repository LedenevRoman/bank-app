package com.training.rledenev.controllers;

import com.training.rledenev.dto.UserDto;
import com.training.rledenev.entity.User;
import com.training.rledenev.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/new-client")
    public ResponseEntity<Long> saveNewClient(@RequestBody UserDto userDto) {
        User user = userService.saveNewClient(userDto);
        return ResponseEntity.created(URI.create("/" + user.getId())).body(user.getId());
    }

    @GetMapping("/{id}")
    public UserDto getAgreementDtoById(@PathVariable(name = "id") Long id) {
        return userService.getUserDtoById(id);
    }
}
