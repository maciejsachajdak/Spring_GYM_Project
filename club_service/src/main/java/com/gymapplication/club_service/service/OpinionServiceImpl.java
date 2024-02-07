package com.gymapplication.club_service.service;

import com.gymapplication.club_service.dto.OpinionDto;
import com.gymapplication.club_service.entity.Club;
import com.gymapplication.club_service.entity.Opinion;
import com.gymapplication.club_service.repository.ClubRepository;
import com.gymapplication.club_service.repository.OpinionRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OpinionServiceImpl implements OpinionService {
    private final OpinionRepository opinionRepository;
    private final ClubRepository clubRepository;

    public OpinionServiceImpl(OpinionRepository opinionRepository, ClubRepository clubRepository) {
        this.opinionRepository = opinionRepository;
        this.clubRepository = clubRepository;
    }

    @Override
    public void addOpinion(OpinionDto opinionDto, Club club) {
        Opinion opinion = new Opinion();
        opinion.setName(opinionDto.getName());
        opinion.setDate(Timestamp.valueOf(LocalDateTime.now()));
        opinion.setRate(opinionDto.getRate());
        opinion.setContent(opinionDto.getContent());
        opinion.setClub(club);

        opinionRepository.save(opinion);

        Club clubFromRepo = clubRepository.findById(club.getId().intValue());
        List<Opinion> allOpinionToClub = opinionRepository.findAllByClubId(club.getId());
        double averageRate = allOpinionToClub.stream().mapToDouble(Opinion::getRate).average().orElse(0.0);
        double roundedAverageRate = Math.round(averageRate * 100.0) / 100.0;
        clubFromRepo.setAverageRate(roundedAverageRate);
        clubRepository.save(clubFromRepo);
    }

    @Override
    public List<Opinion> findAll() {
        return opinionRepository.findAll();
    }

    @Override
    public List<Opinion> findAllToClubId(Club club) {
        return findAll().stream().filter(opinion -> opinion.getClub().equals(club)).toList();
    }
}
