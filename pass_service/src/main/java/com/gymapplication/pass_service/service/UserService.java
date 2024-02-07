package com.gymapplication.pass_service.service;


import com.gymapplication.pass_service.dto.UserDto;
import com.gymapplication.pass_service.entityDbUsers.User;
import com.gymapplication.pass_service.listener.UserListener;

import java.util.List;

public interface UserService {
    List<User> LOGGED_USERS = UserListener.getLOGGED_USERS();
    UserDto loadLastLoggedUser();

    void resignPassToUser(UserDto userDto, Integer id);
    void sendAllUsers();

}
