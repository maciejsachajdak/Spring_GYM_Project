package com.gymapplication.club_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gymapplication.club_service.dto.OpinionDto;
import com.gymapplication.club_service.entity.Club;
import com.gymapplication.club_service.entity.Opinion;
import com.gymapplication.club_service.repository.OpinionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OpinionServiceImpl implements OpinionService {
    private final OpinionRepository opinionRepository;

    public OpinionServiceImpl(OpinionRepository opinionRepository) {
        this.opinionRepository = opinionRepository;
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
