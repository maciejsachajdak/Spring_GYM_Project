package com.gymapplication.user_panel_service.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gymapplication.user_panel_service.entityDbUsers.User;
import com.gymapplication.user_panel_service.repositoryDbUsers.UserRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AllUsersListener {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final UserRepository userRepository;

    public static final List<User> ALL_USERS = new ArrayList<>();

    public AllUsersListener(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @KafkaListener(topics = "project_all_users", groupId = "project_all_users")
    public void processAllUsersFromAnotherService(String user) {
        if(ALL_USERS.size()>=userRepository.count()){
            ALL_USERS.clear();
        }
        System.out.println("You received user from another service: " + user);
        User readUser;
        try {
            readUser = objectMapper.readValue(user, User.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        ALL_USERS.add(readUser);
    }
}
