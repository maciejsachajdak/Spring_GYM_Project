package com.gymapplication.club_service.service;


import com.gymapplication.club_service.dto.UserDto;
import com.gymapplication.club_service.entity.User;
import com.gymapplication.club_service.listener.UserListener;

import java.util.List;

public interface UserService {
    List<User> LOGGED_USERS = UserListener.getLOGGED_USERS();
    UserDto loadLastLoggedUser();

}
