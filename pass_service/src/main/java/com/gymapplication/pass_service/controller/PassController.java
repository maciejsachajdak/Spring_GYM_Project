package com.gymapplication.pass_service.controller;

import com.gymapplication.pass_service.dto.UserDto;
import com.gymapplication.pass_service.entityDbClubsAndPasses.Pass;
import com.gymapplication.pass_service.service.PassService;
import com.gymapplication.pass_service.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PassController {
    private final UserService userService;
    private final PassService passService;
    @Autowired
    private HttpServletRequest request;

    public PassController(UserService userService, PassService passService) {
        this.userService = userService;
        this.passService = passService;
    }

    @ModelAttribute("currentPath")
    public String getCurrentPath() {
        return request.getRequestURI();
    }

    @GetMapping("/ps/passes")
    public String passPanel(Model model, @RequestParam("id") Integer id) {
        UserDto user = userService.loadLastLoggedUser();
        List<Pass> passes = passService.findAll();
        passService.loadImgUrls(passes);
        if (user.getLogin() == null) {
            return "redirect:http://localhost:8080/ps/passes";
        }
        model.addAttribute("user", user);
        model.addAttribute("id", id);
        model.addAttribute("passes", passes);
        return "passes";
    }


    @GetMapping("/ps/passes/save")
    public String passPanelToSave(Model model, @RequestParam("id") Integer id) {
        UserDto user = userService.loadLastLoggedUser();
        model.addAttribute("user", user);
        List<Pass> passes = passService.findAll();
        model.addAttribute("passes", passes);
        userService.resignPassToUser(user, id);
        return "redirect:http://localhost:8080/ps/passes?id="+ id +"&success";
    }


}
