package com.gymapplication.club_service.repository;

import com.gymapplication.club_service.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<Club, Long> {
    Club findById(Integer id);
}
