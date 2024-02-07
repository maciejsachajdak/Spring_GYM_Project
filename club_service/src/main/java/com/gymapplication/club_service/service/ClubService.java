package com.gymapplication.club_service.service;

import com.gymapplication.club_service.entity.Club;

import java.util.List;

public interface ClubService {
    List<Club> findAllClubs();

    Club findById(Integer id);
}
