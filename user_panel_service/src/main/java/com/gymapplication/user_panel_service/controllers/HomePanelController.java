package com.gymapplication.user_panel_service.controllers;

import com.gymapplication.user_panel_service.dto.UserDto;
import com.gymapplication.user_panel_service.entityDbClubsAndPasses.Club;
import com.gymapplication.user_panel_service.entityDbClubsAndPasses.Pass;
import com.gymapplication.user_panel_service.entityDbUsers.User;
import com.gymapplication.user_panel_service.repositoryDbClubsAndPasses.ClubRepository;
import com.gymapplication.user_panel_service.repositoryDbClubsAndPasses.PassRepository;
import com.gymapplication.user_panel_service.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HomePanelController {

    private final PasswordEncoder passwordEncoder;
    @Autowired
    private HttpServletRequest request;
    private final UserService userService;
    private final ClubRepository clubRepository;
    private final PassRepository passRepository;

    public HomePanelController(UserService userService, PasswordEncoder passwordEncoder, ClubRepository clubRepository, PassRepository passRepository) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.clubRepository = clubRepository;
        this.passRepository = passRepository;
    }

    @ModelAttribute("currentPath")
    public String getCurrentPath() {
        return request.getRequestURI();
    }

    @GetMapping("/up/home")
    public String homePanel(Model model) {
        UserDto user = userService.loadLastLoggedUser();
        if (user.getLogin() == null) {
            return "redirect:http://localhost:8080/up/home";
        }
        model.addAttribute("user", user);
        return "home";
    }


    @GetMapping("/up/userPanel")
    public String userPanel(Model model) {
        UserDto user = userService.loadLastLoggedUser();
        model.addAttribute("user", user);
        if(user.getClubNumber()!=null){
            Club club = clubRepository.findById(user.getClubNumber());
            model.addAttribute("club", club);
        }
        if(user.getPassNumber()!=null){
            Pass pass = passRepository.findById(user.getPassNumber());
            model.addAttribute("pass", pass);
        }
        return "userPanel";
    }

    @GetMapping("/up/editAccount")
    public String editAccount(Model model) {
        UserDto user = userService.loadLastLoggedUser();
        model.addAttribute("editUserAccount", user);
        return "editAccount";
    }

    @PostMapping("/up/editAccount/save")
    public String editAccount(@Valid @ModelAttribute("editUserAccount") UserDto user, BindingResult result, Model model) {
        UserDto accountBefore = userService.loadLastLoggedUser();

        User existingEmail = userService.findByEmail(user.getEmail());
        User existingLogin = userService.findByLogin(user.getLogin());

        if (existingEmail != null && !user.getEmail().equals(accountBefore.getEmail())) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if (existingLogin != null && !user.getLogin().equals(accountBefore.getLogin())) {
            result.rejectValue("login", null, "There is already an account registered with that login");
        }

        if (
                result.hasFieldErrors("name") || result.hasFieldErrors("surname") || result.hasFieldErrors("address") ||
                        result.hasFieldErrors("sex") || result.hasFieldErrors("email") || result.hasFieldErrors("login") ||
                        result.hasFieldErrors("birthDate")
        ) {
            model.addAttribute("editUserAccount", user);
            return "editAccount";
        }

        userService.editAccount(user);

        return "redirect:http://localhost:8080/up/editAccount?success";
    }

    @GetMapping("/up/changePassword")
    public String changePasswordPanel(Model model) {
        UserDto user = userService.loadLastLoggedUser();
        model.addAttribute("user", user);
        return "changePassword";
    }

    @PostMapping("/up/changePassword/save")
    public String changePasswordPanel(@Valid @ModelAttribute("user") UserDto user, BindingResult result, Model model) {
        UserDto oldUser = userService.loadLastLoggedUser();
        if (!passwordEncoder.matches(user.getOldPassword(), oldUser.getPassword())) {
            result.rejectValue("oldPassword", null, "You entered the wrong password.");
        }
        if (!user.getNewPassword().equals(user.getConfirmNewPassword())) {
            result.rejectValue("confirmNewPassword", null, "New password and Confirm new password are different.");
        }
        if (passwordEncoder.matches(user.getNewPassword(), oldUser.getPassword())) {
            result.rejectValue("newPassword", null, "This your actually password, change isn't necessary!");
        }
        if (user.getOldPassword() == null) {
            result.rejectValue("newPassword", null, "This field should not be empty.");
        }

        if (result.hasFieldErrors("oldPassword") || result.hasFieldErrors("newPassword") || result.hasFieldErrors("confirmNewPassword")) {
            model.addAttribute("user", user);
            return "changePassword";
        }

        userService.saveNewPassword(user);


        return "redirect:http://localhost:8080/up/changePassword?success";
    }

    @GetMapping("/up/deleteAccount")
    public String deleteAccount(Model model) {
        UserDto user = userService.loadLastLoggedUser();
        model.addAttribute("user", user);
        return "deleteAccount";
    }

    @PostMapping("/up/deleteAccount/confirm")
    public String deleteAccount(@Valid @ModelAttribute("user") UserDto user, BindingResult result, Model model) {
        UserDto oldUser = userService.loadLastLoggedUser();
        if (!passwordEncoder.matches(user.getOldPassword(), oldUser.getPassword())) {
            result.rejectValue("oldPassword", null, "You entered the wrong password.");
        }

        if (result.hasFieldErrors("oldPassword")) {
            model.addAttribute("user", user);
            return "deleteAccount";
        }

        userService.deleteAccount();

        return "redirect:http://localhost:8080/up/deleteAccount?deleteConfirmation";
    }

    @GetMapping("/up/cancelPass")
    public String cancelPass(Model model) {
        UserDto user = userService.loadLastLoggedUser();
        model.addAttribute("user", user);
        userService.cancelPass(user);
        return "redirect:http://localhost:8080/up/userPanel?successCancel";
    }

    @GetMapping("/up/users")
    public String userList(Model model) {
        List<UserDto> listOfRegisteredUsers = userService.listOfRegisteredUsers();
        UserDto userAdmin = userService.loadLastLoggedUser();
        model.addAttribute("userAdmin", userAdmin);
        model.addAttribute("users", listOfRegisteredUsers);
        return "users";
    }

    @GetMapping("/up/editUser")
    public String editUser(@RequestParam(name = "id") Integer Id, Model model) {
        UserDto userToEdit = userService.findUser(Id);
        UserDto userAdmin = userService.loadLastLoggedUser();
        model.addAttribute("userAdmin", userAdmin);
        model.addAttribute("userToEdit", userToEdit);
        return "editUser";
    }

    @PostMapping("/up/editUser/save")
    public String editUser(@ModelAttribute("id") Integer Id, @Valid @ModelAttribute("userToEdit") UserDto user, BindingResult result, Model model) {
        UserDto userToEdit = userService.findUser(Id);
        UserDto userAdmin = userService.loadLastLoggedUser();
        model.addAttribute("userAdmin", userAdmin);

        User existingEmail = userService.findByEmail(user.getEmail());
        User existingLogin = userService.findByLogin(user.getLogin());

        if (existingEmail != null && !user.getEmail().equals(userToEdit.getEmail())) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if (existingLogin != null && !user.getLogin().equals(userToEdit.getLogin())) {
            result.rejectValue("login", null, "There is already an account registered with that login");
        }

        if (
                result.hasFieldErrors("name") || result.hasFieldErrors("surname") || result.hasFieldErrors("address") ||
                        result.hasFieldErrors("sex") || result.hasFieldErrors("email") || result.hasFieldErrors("login") ||
                        result.hasFieldErrors("birthDate")
        ) {
            model.addAttribute("userToEdit", user);
            return "editUser";
        }

        userService.editUser(user, Id);

        return "redirect:http://localhost:8080/up/editUser?id=" + Id + "&success";
    }

    @GetMapping("/up/deleteUser")
    public String deleteUser(@RequestParam(name = "login") String login, Model model) {
        UserDto userAdmin = userService.loadLastLoggedUser();
        model.addAttribute("login", login);
        model.addAttribute("userAdmin", userAdmin);
        return "deleteUser";
    }

    @PostMapping("/up/deleteUser/confirm")
    public String deleteUser(@ModelAttribute(name = "login") String login, @Valid @ModelAttribute("userToDelete") UserDto user, BindingResult result, Model model) {
        UserDto userAdmin = userService.loadLastLoggedUser();
        model.addAttribute("userAdmin", userAdmin);

        if (!passwordEncoder.matches(user.getOldPassword(), userAdmin.getPassword())) {
            result.rejectValue("oldPassword", null, "You entered the wrong password.");
        }

        if (result.hasFieldErrors("oldPassword")) {
            model.addAttribute("userToDelete", user);
            return "deleteUser";
        }

        userService.deleteUser(login);

        return "redirect:http://localhost:8080/up/deleteUser?login=" + login + "&deleteConfirmation";
    }

    @GetMapping("/up/createUser")
    public String createUser(Model model) {
        UserDto userAdmin = userService.loadLastLoggedUser();
        model.addAttribute("userAdmin", userAdmin);
        UserDto userToCreate = new UserDto();
        model.addAttribute("userToCreate", userToCreate);
        return "createUser";
    }

    @PostMapping("/up/createUser/save")
    public String registration(@Valid @ModelAttribute("userToCreate") UserDto user, BindingResult result, Model model) {
        UserDto userAdmin = userService.loadLastLoggedUser();
        model.addAttribute("userAdmin", userAdmin);

        User existingEmail = userService.findByEmail(user.getEmail());
        User existingLogin = userService.findByLogin(user.getLogin());
        if (existingEmail != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if (existingLogin != null) {
            result.rejectValue("login", null, "There is already an account registered with that login");
        }

        if (
                result.hasFieldErrors("name") || result.hasFieldErrors("surname") || result.hasFieldErrors("address") ||
                        result.hasFieldErrors("sex") || result.hasFieldErrors("email") || result.hasFieldErrors("login") ||
                        result.hasFieldErrors("birthDate") || result.hasFieldErrors("clubNumber")
        ) {
            model.addAttribute("userToCreate", user);
            return "createUser";
        }

        userService.saveUser(user);

        return "redirect:http://localhost:8080/up/createUser?success";
    }

}
