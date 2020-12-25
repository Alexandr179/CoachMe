package ru.coach.service.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.coach.service.rerpositories.UserRepository;
import ru.coach.service.security.details.UserDetailsImpl;

@Controller
public class UsersController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public String getUsersPage(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model){
        logger.warn("Authenticated User is: " + userDetails.getUser());// знаем какой юзер в данн.момент.. в приложении

        model.addAttribute("users", userRepository.findAll());
        return "users_page";
    }
}
