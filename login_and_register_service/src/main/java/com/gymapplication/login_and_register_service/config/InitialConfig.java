package com.gymapplication.login_and_register_service.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gymapplication.login_and_register_service.entity.Role;
import com.gymapplication.login_and_register_service.entity.User;
import com.gymapplication.login_and_register_service.repository.RoleRepository;
import com.gymapplication.login_and_register_service.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;

@Component
public class InitialConfig {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public InitialConfig(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        if (roleRepository.findAll().isEmpty()) {
            makeRoles();
        }

        if (userRepository.findAll().isEmpty()) {
            addUser(1L, "John", "Wick", "Hotel Continental", "Male", Date.valueOf("1964-10-09"), "johnWick", "john@wick.admin", "Wick123!", "ADMIN", null);
            addUser(2L, "Jan", "Wianek", "Motel Kontynent", "Male", Date.valueOf("1965-09-10"), "janWianek", "jan@wianek.admin", "Janek123!", "ADMIN", null);
            addUser(3L, "Dariusz", "Golfista", "Dolkowa 28", "Male", Date.valueOf("1999-12-18"), "darek", "darek@golf.pl", "Darek123!", "USER", 1);
            addUser(4L, "Robert", "Wyscigowiec", "Szybka 12", "Male", Date.valueOf("2000-01-10"), "robert", "robert@gmail.pl", "Robert123!", "USER", 2);
            addUser(5L, "Monika", "Ruda", "Pokatna", "Female", Date.valueOf("1995-05-05"), "monika", "monika@o2.pl", "Monika123!", "USER", 3);
            addUser(6L, "Bob", "Budowniczy", "Koparkowa 15", "Male", Date.valueOf("1980-08-09"), "bob", "bob@interia.pl", "Bobek123!", "USER", 4);
        }
    }
    private void addUser(Long id, String name, String surname, String address, String sex, Date birthDate,
                         String login, String email, String password, String roleName, Integer clubNumber) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setSurname(surname);
        user.setAddress(address);
        user.setSex(sex);
        user.setBirthDate(birthDate);
        user.setLogin(login);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(roleRepository.findByName(roleName));
        user.setRoleName(roleName);
        user.setClubNumber(clubNumber);
        userRepository.save(user);
    }

    private void makeRoles() {
        Role roleAdmin = new Role();
        roleAdmin.setName("ADMIN");
        roleRepository.save(roleAdmin);
        Role roleUser = new Role();
        roleUser.setName("USER");
        roleRepository.save(roleUser);
    }
}
