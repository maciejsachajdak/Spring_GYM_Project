package com.gymapplication.club_service.controller;

import com.gymapplication.club_service.dto.OpinionDto;
import com.gymapplication.club_service.dto.UserDto;
import com.gymapplication.club_service.entity.Club;
import com.gymapplication.club_service.entity.Opinion;
import com.gymapplication.club_service.service.ClubService;
import com.gymapplication.club_service.service.OpinionService;
import com.gymapplication.club_service.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ClubController {
    private final UserService userService;
    @Autowired
    private HttpServletRequest request;
    private final ClubService clubService;
    private final OpinionService opinionService;

    public ClubController(UserService userService, ClubService clubService, OpinionService opinionService) {
        this.userService = userService;
        this.clubService = clubService;
        this.opinionService = opinionService;
    }

    @ModelAttribute("currentPath")
    public String getCurrentPath() {
        return request.getRequestURI();
    }

    @GetMapping("/cs/clubs")
    public String homePanel(Model model) {
        UserDto user = userService.loadLastLoggedUser();
        List<Club> clubs = clubService.findAllClubs();
        if (user.getLogin() == null) {
            return "redirect:http://localhost:8080/cs/clubs";
        }
        model.addAttribute("user", user);
        model.addAttribute("allClubs", clubs);
        return "clubs";
    }

    @GetMapping("/cs/clubPanel")
    public String clubPanel(Model model, @RequestParam("id") Integer id) {
        UserDto user = userService.loadLastLoggedUser();
        Club club = clubService.findById(id);
        List<Opinion> opinions = opinionService.findAllToClubId(club);
        model.addAttribute("user", user);
        model.addAttribute("club", club);
        model.addAttribute("opinions", opinions);
        return "clubPanel";
    }

    @GetMapping("/cs/addOpinion")
    public String addingOpinion(Model model, @RequestParam("id") Integer id) {
        UserDto user = userService.loadLastLoggedUser();
        Club club = clubService.findById(id);
        OpinionDto opinion = new OpinionDto();
        model.addAttribute("user", user);
        model.addAttribute("club", club);
        model.addAttribute("opinion", opinion);
        return "addOpinion";
    }

    @PostMapping("/cs/addOpinion/save")
    public String addingOpinion(@Valid @ModelAttribute("opinion") OpinionDto opinion, BindingResult result, Model model, @RequestParam("id") Integer id) {
        UserDto user = userService.loadLastLoggedUser();
        Club club = clubService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("club", club);
        if(result.hasErrors()){
            model.addAttribute("opinion", opinion);
            return "addOpinion";
        }

        opinionService.addOpinion(opinion, club);

        return "redirect:http://localhost:8080/cs/addOpinion?id="+ id +"&success";
    }


}
