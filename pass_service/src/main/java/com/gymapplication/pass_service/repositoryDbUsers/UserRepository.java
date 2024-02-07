package com.gymapplication.pass_service.repositoryDbUsers;

import com.gymapplication.pass_service.entityDbUsers.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByLogin(String login);
}
