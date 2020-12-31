package ru.coach.service.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.coach.service.security.authentication.TokenOrNameAuthentication;

@Controller
@RequestMapping("/")
public class RootController {

    @Value("${application.root.redirect}")
    private String redirectUrl;

    @GetMapping
    public String getRootPage(TokenOrNameAuthentication authentication){
        if (authentication == null) {
            return "redirect:/signIn";
        } else {
            return "redirect:" + redirectUrl;
        }
    }
}
