package com.gymapplication.login_and_register_service.repository;

import com.gymapplication.login_and_register_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByLogin(String login);
}
