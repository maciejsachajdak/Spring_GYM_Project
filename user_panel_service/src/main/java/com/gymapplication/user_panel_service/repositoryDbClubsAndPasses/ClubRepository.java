package com.gymapplication.user_panel_service.repositoryDbClubsAndPasses;

import com.gymapplication.user_panel_service.entityDbClubsAndPasses.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubRepository extends JpaRepository<Club,Long> {
    Club findById(Integer id);
}
