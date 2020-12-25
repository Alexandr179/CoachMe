package ru.coach.service.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class RootController {

    @Value("${application.root.redirect}")// application.properties
    private String redirectUrl;

    @GetMapping
    public String getRootPage(Authentication authentication){
        if (authentication == null) {
            return "redirect:/signIn";
        } else {
            return "redirect:" + redirectUrl;
        }
    }
}
