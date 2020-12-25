package ru.coach.service.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SignInController {

    @GetMapping("/signIn")// ..., @RequestParam("error") String error
    public String getSignInPage(Authentication authentication){// внимательнее, сожно схватить не тот пакет (security)
        if (authentication == null) {
            return "sign_in_page";
        } else {
            return "redirect:/";
        }
    }
}
