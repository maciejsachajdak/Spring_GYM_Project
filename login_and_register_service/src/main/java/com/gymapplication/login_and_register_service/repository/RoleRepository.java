package com.gymapplication.login_and_register_service.repository;

import com.gymapplication.login_and_register_service.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
