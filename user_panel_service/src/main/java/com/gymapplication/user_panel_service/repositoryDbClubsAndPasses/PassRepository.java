package com.gymapplication.user_panel_service.repositoryDbClubsAndPasses;

import com.gymapplication.user_panel_service.entityDbClubsAndPasses.Pass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassRepository extends JpaRepository<Pass,Long> {
    Pass findById(Integer id);
}
