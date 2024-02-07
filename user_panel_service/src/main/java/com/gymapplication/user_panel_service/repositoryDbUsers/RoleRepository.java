package com.gymapplication.user_panel_service.repositoryDbUsers;

import com.gymapplication.user_panel_service.entityDbUsers.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
