package com.example.demo.controller;

import com.example.demo.dto.UpdateUserDtoIn;
import com.example.demo.dto.UserDtoIn;
import com.example.demo.dto.UserDtoOut;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @GetMapping("{id}")
    public UserDtoOut getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PostMapping
    public UserDtoOut addUser(@Valid @RequestBody UserDtoIn userDtoIn) {
        return userService.addUser(userDtoIn);
    }

    @PutMapping("{id}")
    public UserDtoOut updateUser(@PathVariable Long id,
                                 @Valid @RequestBody UpdateUserDtoIn updateUserDtoIn) {
        return userService.updateUser(id, updateUserDtoIn);
    }
}
