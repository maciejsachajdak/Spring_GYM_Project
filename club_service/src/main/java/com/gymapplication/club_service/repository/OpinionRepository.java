package com.gymapplication.club_service.repository;

import com.gymapplication.club_service.entity.Opinion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpinionRepository extends JpaRepository<Opinion, Long> {
    List<Opinion> findAllByClubId(Long clubId);
}
