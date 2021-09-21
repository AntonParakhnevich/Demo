package com.example.demo.service;


import com.example.demo.dto.UpdateUserDtoIn;
import com.example.demo.dto.UserDtoIn;
import com.example.demo.dto.UserDtoOut;

public interface UserService {
    UserDtoOut getUser(Long id);

    UserDtoOut addUser(UserDtoIn userDtoIn);

    UserDtoOut updateUser(Long userId, UpdateUserDtoIn updateUserDtoIn);

}
