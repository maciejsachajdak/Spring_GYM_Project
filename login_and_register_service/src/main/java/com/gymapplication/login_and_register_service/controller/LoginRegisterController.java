package com.gymapplication.login_and_register_service.controller;

import com.gymapplication.login_and_register_service.dto.UserDto;
import com.gymapplication.login_and_register_service.entity.User;
import com.gymapplication.login_and_register_service.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class LoginRegisterController {

    private UserService userService;

    public LoginRegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/lr/login")
    public String loginForm(Model model, @CookieValue(value = "tokenError", required = false) String error, HttpServletResponse response) {
        if(error!=null){
            if(error.equals("true")){
                model.addAttribute("errorWithToken", true);
                Cookie cookie = new Cookie("tokenError", null);
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }
        return "login";
    }

    @GetMapping("/lr/register")
    public String showRegistrationForm(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/lr/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto user, BindingResult result, Model model) {
        User existingEmail = userService.findByEmail(user.getEmail());
        User existingLogin = userService.findByLogin(user.getLogin());
        if (existingEmail != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if (existingLogin != null) {
            result.rejectValue("login", null, "There is already an account registered with that login");
        }

        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }

        userService.saveUser(user);
        return "redirect:http://localhost:8080/lr/register?success";
    }

    @GetMapping("/lr/logout")
    public String logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("JWT_TOKEN", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        return "redirect:http://localhost:8080/lr/login";
    }
}
