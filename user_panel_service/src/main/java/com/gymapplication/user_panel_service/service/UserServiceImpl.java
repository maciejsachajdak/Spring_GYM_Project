package com.gymapplication.user_panel_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gymapplication.user_panel_service.dto.UserDto;
import com.gymapplication.user_panel_service.entityDbUsers.User;
import com.gymapplication.user_panel_service.listener.AllUsersListener;
import com.gymapplication.user_panel_service.repositoryDbUsers.RoleRepository;
import com.gymapplication.user_panel_service.repositoryDbUsers.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ObjectMapper objectMapper;

    @Value("${user.topic.name}")
    private String topicUser;

    @Value("${allUsers.topic.name}")
    private String topicAllUsers;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository, ObjectMapper objectMapper, KafkaTemplate<String, String> kafkaTemplate) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.objectMapper = objectMapper;
        this.kafkaTemplate = kafkaTemplate;
    }


    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setAddress(userDto.getAddress());
        user.setBirthDate(userDto.getBirthDate());
        user.setSex(userDto.getSex());
        user.setLogin(userDto.getLogin());
        user.setClubNumber(userDto.getClubNumber());
        user.setEmail(userDto.getEmail());

        user.setPassword(passwordEncoder.encode("User123!"));

        user.setRole(roleRepository.findByName("USER"));
        user.setRoleName("USER");
        userRepository.save(user);
        sendAllUsers();
    }

    @Override
    public void saveNewPassword(UserDto userDto) {
        if (!LOGGED_USERS.isEmpty()) {
            User lastLoggedUser = LOGGED_USERS.get(LOGGED_USERS.size() - 1);
            User user = userRepository.findByLogin(lastLoggedUser.getLogin());
            user.setPassword(passwordEncoder.encode(userDto.getNewPassword()));
            userRepository.save(user);
        }
        sendAllUsers();
    }

    public UserDto loadLastLoggedUser() {
        UserDto userDto = new UserDto();
        if (!LOGGED_USERS.isEmpty()) {
            User lastLoggedUser = LOGGED_USERS.get(LOGGED_USERS.size() - 1);
            userDto.setId(lastLoggedUser.getId());
            userDto.setName(lastLoggedUser.getName());
            userDto.setSurname(lastLoggedUser.getSurname());
            userDto.setAddress(lastLoggedUser.getAddress());
            userDto.setSex(lastLoggedUser.getSex());
            userDto.setBirthDate(lastLoggedUser.getBirthDate());
            userDto.setLogin(lastLoggedUser.getLogin());
            userDto.setEmail(lastLoggedUser.getEmail());
            userDto.setPassword(lastLoggedUser.getPassword());
            userDto.setRoleName(lastLoggedUser.getRoleName());
            userDto.setClubNumber(lastLoggedUser.getClubNumber());
            userDto.setPassNumber(lastLoggedUser.getPassNumber());
        }
        return userDto;
    }

    @Override
    public void deleteAccount() {
        if (!LOGGED_USERS.isEmpty()) {
            User lastLoggedUser = LOGGED_USERS.get(LOGGED_USERS.size() - 1);
            User userToDelete = userRepository.findByLogin(lastLoggedUser.getLogin());
            userRepository.delete(userToDelete);
        }
        sendAllUsers();
    }

    @Override
    public List<UserDto> listOfRegisteredUsers() {
        List<User> userList;
        if (AllUsersListener.ALL_USERS.isEmpty()) {
            userList = userRepository.findAll();
        } else {
            userList = AllUsersListener.ALL_USERS;
        }
        return userList.stream()
                .filter(user -> user.getRoleName().equals("USER"))
                .map(this::convertEntityToDto)
                .toList();
    }

    @Override
    public UserDto findUser(Integer Id) {
        return convertEntityToDto(userRepository.findById(Id));
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public void editUser(UserDto userDto, Integer Id) {
        User user = userRepository.findById(Id);
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setAddress(userDto.getAddress());
        user.setBirthDate(userDto.getBirthDate());
        user.setSex(userDto.getSex());
        user.setLogin(userDto.getLogin());
        user.setEmail(userDto.getEmail());
        userRepository.save(user);
        sendAllUsers();
    }

    @Override
    public void deleteUser(String login) {
        userRepository.delete(userRepository.findByLogin(login));
        sendAllUsers();
    }

    @Override
    public void editAccount(UserDto userDto) {
        User user = userRepository.findByLogin(loadLastLoggedUser().getLogin());
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setAddress(userDto.getAddress());
        user.setBirthDate(userDto.getBirthDate());
        user.setSex(userDto.getSex());
        user.setLogin(userDto.getLogin());
        user.setEmail(userDto.getEmail());
        user.setClubNumber(userDto.getClubNumber());
        userRepository.save(user);
        try {
            String userString = objectMapper.writeValueAsString(user);
            kafkaTemplate.send(topicUser, userString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        sendAllUsers();
    }

    @Override
    public void cancelPass(UserDto userDto) {
        User user = userRepository.findByLogin(loadLastLoggedUser().getLogin());
        user.setPassNumber(null);
        userRepository.save(user);
        try {
            String userString = objectMapper.writeValueAsString(user);
            kafkaTemplate.send(topicUser, userString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
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

    private UserDto convertEntityToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setSurname(user.getSurname());
        userDto.setAddress(user.getAddress());
        userDto.setBirthDate(user.getBirthDate());
        userDto.setSex(user.getSex());
        userDto.setLogin(user.getLogin());
        userDto.setClubNumber(user.getClubNumber());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setRoleName(user.getRoleName());
        if(user.getPassNumber()!=null){
            userDto.setPassNumber(user.getPassNumber());
        }
        return userDto;
    }
}
