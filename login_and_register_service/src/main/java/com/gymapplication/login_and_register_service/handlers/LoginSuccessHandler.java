package com.gymapplication.login_and_register_service.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gymapplication.login_and_register_service.config.JwtTokenGenerator;
import com.gymapplication.login_and_register_service.entity.User;
import com.gymapplication.login_and_register_service.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Value("${user.topic.name}")
    private String topicName;

    public LoginSuccessHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByLogin(userDetails.getUsername());
        try {
            String userString = objectMapper.writeValueAsString(user);
            kafkaTemplate.send(topicName, userString);

            Cookie cookie = new Cookie("JWT_TOKEN", JwtTokenGenerator.generateToken(userDetails.getUsername()));
            cookie.setPath("/");
            response.addCookie(cookie);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        redirectStrategy.sendRedirect(request, response, "http://localhost:8080/up/home");

        clearAuthenticationAttributes(request);
    }


}
