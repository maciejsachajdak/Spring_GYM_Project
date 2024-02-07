package com.gymapplication.club_service.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gymapplication.club_service.entity.Club;
import com.gymapplication.club_service.entity.Opinion;
import com.gymapplication.club_service.repository.ClubRepository;
import com.gymapplication.club_service.repository.OpinionRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

@Component
public class InitialConfig {

    private final ClubRepository clubRepository;
    private final OpinionRepository opinionRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public InitialConfig(ClubRepository clubRepository, OpinionRepository opinionRepository) {
        this.clubRepository = clubRepository;
        this.opinionRepository = opinionRepository;
    }


    @PostConstruct
    public void init() {
        if (clubRepository.findAll().isEmpty()) {
            addCLub(1L, "Turbo Gym", "Polna 30", "8.00-19.00", "Turbo Gym is a high-energy fitness destination located in the heart of the city. Boasting cutting-edge exercise machines and a diverse range of free weights, Turbo Gym caters to fitness enthusiasts of all levels. The gym offers personalized training programs, group classes, and a dynamic atmosphere that motivates members to achieve their fitness goals. With certified trainers and a variety of cardio and strength training options, Turbo Gym provides a comprehensive fitness experience.");
            addCLub(2L, "Lion Roar", "Długa 17", "7.30-20.00", "Lion Roar Fitness Center stands out for its commitment to intensity and results-driven workouts. The gym features a wide array of strength and conditioning equipment, catering to individuals focused on building muscle, increasing endurance, and achieving peak athletic performance. With dedicated powerlifting and CrossFit zones, Lion Roar encourages a competitive yet supportive environment. Trained coaches are available for personalized guidance, and the center often hosts fitness competitions and challenges to keep members motivated.");
            addCLub(3L, "Fast Dolphin", "Pokątna 137", "8.30-22.00", "Fast Dolphin Athletic Club is a premier destination for those seeking a swift and dynamic workout experience. Boasting a variety of workout options, including yoga studios, functional training areas, and high-tech cardio equipment, this gym is designed for holistic well-being. Fast Dolphin also provides nutritional counseling and wellness workshops to support members on their fitness journey.");
            addCLub(4L, "Strong Raccoon", "Silna 1", "7.00-22.00", "Strong Raccoon Wellness Studio is a unique blend of traditional fitness and mindfulness practices. The gym emphasizes the connection between physical and mental well-being, offering yoga and meditation classes alongside a fully equipped fitness area. The ambiance is serene, with calming colors and natural elements creating a tranquil space for members to unwind and rejuvenate. In addition to standard workout facilities, Strong Raccoon offers mindfulness workshops, stress-relief programs, and holistic health services, promoting a balanced approach to fitness and wellness.");

            if (opinionRepository.findAll().isEmpty()) {
                addOpinion(1L, "Elite Gym Experience.", 5, Timestamp.valueOf("2023-12-24 17:30:50"), "A Fitness Haven with Cutting-Edge Equipment and Welcoming Staff.", 1L);
                addOpinion(2L, "Peak Hour Frustration.", 2, Timestamp.valueOf("2022-10-12 15:30:10"), "Cramped Quarters During Rush Hours, Hindering Accessibility.", 1L);
                addOpinion(3L, "Spotless Serenity.", 4, Timestamp.valueOf("2023-07-24 10:25:50"), "Immaculate Facilities Provide a Serene Workout Environment.", 2L);
                addOpinion(4L, "Parking Woes.", 2, Timestamp.valueOf("2023-08-24 16:00:20"), "Parking Predicament: Limited Spaces for Gym-Goers on Wheels.", 2L);
                addOpinion(5L, "Diverse Fitness Delight.", 4, Timestamp.valueOf("2022-11-22 11:11:11"), "Dynamic Fitness Classes Tailored to Diverse Preferences.", 3L);
                addOpinion(6L, "Ventilation Struggles.", 2, Timestamp.valueOf("2023-06-24 17:30:50"), "Ventilation Woes in the Weightlifting Zone - Uncomfortable.", 3L);
                addOpinion(7L, "Always Open.", 4, Timestamp.valueOf("2023-12-12 12:12:12"), "Anytime Fitness: 24/7 Accessibility for Your Convenience.", 4L);
                addOpinion(8L, "Membership Costs.", 2, Timestamp.valueOf("2022-01-02 14:24:34"), "Membership Pains: High Fees and Extra Charges for Classes.", 4L);
            }

            clubsAddAverage();
        }
    }

    private void clubsAddAverage() {
        clubRepository.findAll().forEach(club -> {
                    List<Opinion> allOpinionsToClub = opinionRepository.findAllByClubId(club.getId());
                    if (!allOpinionsToClub.isEmpty()) {
                        club.setAverageRate(allOpinionsToClub.stream()
                                .mapToDouble(Opinion::getRate)
                                .average()
                                .orElse(0.0));
                        clubRepository.save(club);
                    }
                }
        );
    }

    private void addCLub(Long id, String name, String address, String openHours, String description) {
        Club club = new Club();
        club.setId(id);
        club.setName(name);
        club.setAddress(address);
        club.setOpenHours(openHours);
        club.setDescription(description);
        club.setAverageRate(0.0);

        clubRepository.save(club);

    }

    private void addOpinion(Long id, String name, Integer rate, Timestamp date, String content, Long club_Id) {
        Opinion opinion = new Opinion();
        opinion.setId(id);
        opinion.setName(name);
        opinion.setRate(rate);
        opinion.setDate(date);
        opinion.setContent(content);
        opinion.setClub(clubRepository.getReferenceById(club_Id));

        opinionRepository.save(opinion);
    }
}
