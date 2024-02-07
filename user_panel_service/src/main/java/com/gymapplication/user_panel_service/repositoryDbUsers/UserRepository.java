package com.gymapplication.user_panel_service.repositoryDbUsers;

import com.gymapplication.user_panel_service.entityDbUsers.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByLogin(String login);

    User findById(Integer Id);
}
