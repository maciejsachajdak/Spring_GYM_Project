package com.gymapplication.pass_service.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gymapplication.pass_service.entityDbUsers.User;
import lombok.Getter;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserListener {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Getter
    private static final List<User> LOGGED_USERS = new ArrayList<>();

    @KafkaListener(topics = "project_user", groupId = "project_pass")
    public void processUserFromAnotherService(String user) {
        System.out.println("You received user who's logged into system in another service: " + user);
        User readUser;
        try {
            readUser = objectMapper.readValue(user, User.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        LOGGED_USERS.add(readUser);
    }
}
