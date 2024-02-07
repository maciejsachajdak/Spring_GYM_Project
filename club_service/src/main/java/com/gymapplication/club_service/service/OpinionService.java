package com.gymapplication.club_service.service;

import com.gymapplication.club_service.dto.OpinionDto;
import com.gymapplication.club_service.entity.Club;
import com.gymapplication.club_service.entity.Opinion;

import java.util.List;

public interface OpinionService {
    void addOpinion(OpinionDto opinion, Club club);

    List<Opinion> findAll();

    List<Opinion> findAllToClubId(Club club);
}
