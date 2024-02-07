package com.gymapplication.login_and_register_service.service;

import com.gymapplication.login_and_register_service.dto.UserDto;
import com.gymapplication.login_and_register_service.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findByEmail(String email);

    User findByLogin(String login);

    List<UserDto> findAllUsers();
}
