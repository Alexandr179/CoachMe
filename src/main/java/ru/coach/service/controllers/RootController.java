package ru.coach.service.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.coach.service.security.authentication.TokenAndPassAuthentication;

@Controller
@RequestMapping("/")
public class RootController {

    @Value("${application.root.redirect}")// application.properties
    private String redirectUrl;

    @GetMapping
    public String getRootPage(TokenAndPassAuthentication authentication){// ищем ошибку
        if (authentication == null) {
            return "redirect:/signIn";
        } else {
            return "redirect:" + redirectUrl;
        }
    }
}
