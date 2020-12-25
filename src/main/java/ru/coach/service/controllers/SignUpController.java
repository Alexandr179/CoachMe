package ru.coach.service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.coach.service.forms.UserForm;
import ru.coach.service.services.SignUpService;

@Controller
public class SignUpController {

    @Autowired
    private SignUpService signUpService;

    @PostMapping("/signUp")
    public String signUpUser(UserForm userForm){// userForm << принимаем с фронта не отдельные поля
        signUpService.signUp(userForm);
        return "redirect:/signIn";
    }

    @GetMapping("/signUp")
    public String getSignUpPage(Authentication authentication) {
        if (authentication == null) {
            return "sign_up_page";
        } else {
            return "redirect:/";
        }
    }
}
