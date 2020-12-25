package ru.coach.service.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.coach.service.security.authentication.TokenAndPassAuthentication;

@Controller
public class SignInController {

    // теория 23:00 Spring Security with Spring Boot..
    /** для отслеживания текущей аутентификации ..вводим Authentication **/

    @GetMapping("/signIn")// ..., @RequestParam("error") String error
    public String getSignInPage(TokenAndPassAuthentication authentication){// ищем ошибку
        if (authentication == null) {
            return "sign_in_page";
        } else {
            return "redirect:/";
        }
    }
}
