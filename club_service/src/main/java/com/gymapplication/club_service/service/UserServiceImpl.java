package com.gymapplication.club_service.service;
import com.gymapplication.club_service.dto.UserDto;
import com.gymapplication.club_service.entity.User;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    public UserDto loadLastLoggedUser() {
        UserDto userDto = new UserDto();
        if (!LOGGED_USERS.isEmpty()) {
            User lastLoggedUser = LOGGED_USERS.get(LOGGED_USERS.size() - 1);
            userDto.setId(lastLoggedUser.getId());
            userDto.setName(lastLoggedUser.getName());
            userDto.setLogin(lastLoggedUser.getLogin());
            userDto.setRoleName(lastLoggedUser.getRoleName());
            userDto.setClubNumber(lastLoggedUser.getClubNumber());
        }
        return userDto;
    }
}
