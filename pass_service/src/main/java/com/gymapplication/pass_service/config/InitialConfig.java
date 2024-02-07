package com.gymapplication.pass_service.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gymapplication.pass_service.entityDbClubsAndPasses.Pass;
import com.gymapplication.pass_service.entityDbUsers.User;
import com.gymapplication.pass_service.repositoryDbClubsAndPasses.PassRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InitialConfig {

    private final PassRepository passRepository;

    @Autowired
    public InitialConfig(PassRepository passRepository) {
        this.passRepository = passRepository;
    }


    @PostConstruct
    public void init() {
        if (passRepository.findAll().isEmpty()) {
            addPass(0L, "Basic Gym Access", 1, 1, 12.50, "Get unrestricted entry to our well-equipped gym floor with state-of-the-art machines and free weights, perfect for all fitness levels.");
            addPass(1L, "Cardio Blast Pass", 4, 1, 20.00, "Access our extensive range of cardio equipment including treadmills, stationary bikes, and ellipticals for an intense cardio workout.");
            addPass(2L, "Strength Training Membership", 8, 1, 25.99, "Utilize our specialized strength training area featuring a variety of machines and equipment designed to build muscle and increase strength.");
            addPass(3L, "Group Fitness Pass", 12, 1, 32.00, "Join any of our invigorating group fitness classes led by certified instructors, ranging from yoga and pilates to high-intensity interval training.");
            addPass(4L, "Personal Training Package", 0, 1, 40.50, "Receive personalized attention and guidance from our experienced trainers to help you achieve your fitness goals effectively.");
            addPass(5L, "Student Gym Pass", 4, 2, 17.50, "Exclusive access for students looking to stay fit and active, offering flexible hours to accommodate busy schedules.");
            addPass(6L, "Senior Fitness Pass", 8, 2, 19.99, "Tailored for seniors, this membership provides access to low-impact exercises and specialized classes aimed at improving mobility and overall health.");
            addPass(7L, "Premium Gym Membership", 12, 2, 24.50, "Unlock VIP perks including priority access to facilities, complimentary classes, and discounts on additional services.");
            addPass(8L, "Unlimited Gym Access", 0, 2, 45.99, "Experience the ultimate freedom with unlimited access to all gym facilities, classes, and amenities for a comprehensive fitness experience.");
            addPass(9L, "Weekend Warrior Pass", 4, 0, 26.70, "Perfect for those with busy weekdays, this pass grants access to the gym specifically on weekends, ensuring you never miss a workout.");
            addPass(10L, "Early Bird Membership", 8, 0, 39.99, "Get ahead of the day with early access to the gym, allowing you to kickstart your mornings with energizing workouts before the crowds.");
            addPass(11L, "Flexible Membership", 12, 0, 45.00, "Customize your gym experience with this flexible membership option, allowing you to pay as you go or choose from various membership durations.");
            addPass(12L, "Corporate Gym Pass", 0, 0, 60.50, "Encourage workplace wellness by providing employees with access to our gym facilities, promoting a healthy and active lifestyle within your organization.");
        }

    }



    private void addPass(Long id, String name, Integer entryNumber, Integer availableClubNumber, Double price, String description) {
        Pass pass = new Pass();
        pass.setId(id);
        pass.setName(name);
        pass.setEntryNumber(entryNumber);
        pass.setAvailableClubsNumber(availableClubNumber);
        pass.setPrice(price);
        pass.setDescription(description);

        passRepository.save(pass);
    }

}
