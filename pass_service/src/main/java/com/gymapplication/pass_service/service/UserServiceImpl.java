package com.gymapplication.pass_service.service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gymapplication.pass_service.dto.UserDto;
import com.gymapplication.pass_service.entityDbUsers.User;
import com.gymapplication.pass_service.repositoryDbUsers.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${user.topic.name}")
    private String topicUser;

    @Value("${allUsers.topic.name}")
    private String topicAllUsers;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public UserServiceImpl(UserRepository userRepository, KafkaTemplate<String, String> kafkaTemplate) {
        this.userRepository = userRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public UserDto loadLastLoggedUser() {
        UserDto userDto = new UserDto();
        if (!LOGGED_USERS.isEmpty()) {
            User lastLoggedUser = LOGGED_USERS.get(LOGGED_USERS.size() - 1);
            userDto.setId(lastLoggedUser.getId());
            userDto.setName(lastLoggedUser.getName());
            userDto.setLogin(lastLoggedUser.getLogin());
            userDto.setRoleName(lastLoggedUser.getRoleName());
            userDto.setClubNumber(lastLoggedUser.getClubNumber());
        }
        return userDto;
    }

    @Override
    public void resignPassToUser(UserDto userDto,Integer id) {
        User user = userRepository.findByLogin(userDto.getLogin());
        user.setPassNumber(id);
        try {
            String userString = objectMapper.writeValueAsString(user);
            kafkaTemplate.send(topicUser, userString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        userRepository.save(user);
        sendAllUsers();
    }

    @Override
    public void sendAllUsers() {
        List<User> allUsers = userRepository.findAll();
        for (User user : allUsers) {
            try {
                String userString = objectMapper.writeValueAsString(user);
                kafkaTemplate.send(topicAllUsers, userString);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
