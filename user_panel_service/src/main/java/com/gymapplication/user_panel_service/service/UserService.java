package com.gymapplication.user_panel_service.service;

import com.gymapplication.user_panel_service.dto.UserDto;
import com.gymapplication.user_panel_service.entityDbUsers.User;
import com.gymapplication.user_panel_service.listener.UserListener;

import java.util.List;

public interface UserService {
    List<User> LOGGED_USERS = UserListener.getLOGGED_USERS();
    void saveUser(UserDto userDto);
    void saveNewPassword(UserDto userDto);

    UserDto loadLastLoggedUser();

    void deleteAccount();

    List<UserDto> listOfRegisteredUsers();

    UserDto findUser(Integer Id);

    User findByEmail(String email);

    User findByLogin(String login);

    void editUser(UserDto user, Integer Id);

    void deleteUser(String login);

    void editAccount(UserDto userDto);

    void cancelPass(UserDto user);

    void sendAllUsers();
}
