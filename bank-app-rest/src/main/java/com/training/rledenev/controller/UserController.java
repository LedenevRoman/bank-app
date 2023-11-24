package com.training.rledenev.controller;

import com.training.rledenev.dto.UserDto;
import com.training.rledenev.entity.User;
import com.training.rledenev.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Long> saveNewClient(@Valid @RequestBody UserDto userDto) {
        User user = userService.saveNewClient(userDto);
        return ResponseEntity.created(URI.create("/" + user.getId())).body(user.getId());
    }

    @GetMapping("/{id}")
    public UserDto getUserDtoById(@PathVariable(name = "id") Long id) {
        return userService.getUserDtoById(id);
    }
}
