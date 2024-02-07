package com.gymapplication.club_service.service;

import com.gymapplication.club_service.entity.Club;
import com.gymapplication.club_service.repository.ClubRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClubServiceImpl implements ClubService {
    private final ClubRepository clubRepository;

    public ClubServiceImpl(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }

    @Override
    public List<Club> findAllClubs() {
        List<Club> clubs = clubRepository.findAll();
        clubs.forEach(club -> club.setImgUrl("/cs/img/" + club.getId() + ".jpg"));
        return clubs;
    }

    @Override
    public Club findById(Integer id) {
        Club club = clubRepository.findById(id);
        club.setImgUrl("/cs/img/" + club.getId() + ".jpg");
        return club;
    }
}
