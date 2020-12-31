package ru.coach.service.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.coach.service.forms.UserForm;
import ru.coach.service.security.authentication.TokenOrNameAuthentication;

@Controller
public class SignInController {

    @GetMapping("/signIn")
    public String getSignInPage(TokenOrNameAuthentication authentication){
        if (authentication == null) {
            return "sign_in_page";
        } else {
            return "redirect:/";
        }
    }
}
