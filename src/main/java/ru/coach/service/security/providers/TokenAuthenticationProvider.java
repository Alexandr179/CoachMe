/*
 * 44.Rest_API_Spring_Boot. 19.12.2020, 13:29 / 19.12.2020, 13:29   @A.Alexandr
 * Copyright (c)
 */
package ru.coach.service.security.providers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.coach.service.models.User;
import ru.coach.service.rerpositories.UserRepository;
import ru.coach.service.security.authentication.TokenAndPassAuthentication;
import ru.coach.service.security.details.UserDetailsImpl;

import java.util.Optional;

/**
 * Authentication положили в контекст: SecurityContextHolder. чтобы аутентификация была проведена -> вводим Provider
 * если провайдер делает setAuthentication() = true; .. то запрос проходит в Controller !!
 * --------------------------------------------------------------------------------------------------------------------
 * Provider говорит -> какую !именно аутентификацию он проверяет
 */

@Component
public class TokenAuthenticationProvider implements AuthenticationProvider {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static boolean isRestTokenAuth;

    @Autowired
    @Qualifier("customUserDetailsService")// без  @Qualifier 'падаем'!! в реализацию springSecurity..
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        logger.info("PROVIDER. authenticate(authentication): " + authentication.getName());
        UserDetails userDetails = null;
        TokenAndPassAuthentication tokenAndPassAuthentication = (TokenAndPassAuthentication) authentication;
        /**
         * если аутентификация по REST-запросу (token in header..) не прошла, должна пройти авторизация (всегда) по html
         */
        //TODO: реализация под REST: header - has token
        Optional<User> userOptional = userRepository.findByToken(tokenAndPassAuthentication.getName());
        if (userOptional.isPresent()) {
            isRestTokenAuth = true;
            userDetails = new UserDetailsImpl(userOptional.get());
            logger.info("PROVIDER. userRepository.findByToken(..): " + userDetails.getUsername());
        } else {
            //TODO: реализация под web: html-form has hidden-token
            userDetails = userDetailsService.loadUserByUsername(tokenAndPassAuthentication.getName());
            logger.info("PROVIDER. userDetailsService.loadUserByUsername(..): " + userDetails.getUsername());
        }

        if (userDetails == null) {
            throw new UsernameNotFoundException("PROVIDER. User not found");
        }

        tokenAndPassAuthentication.setAuthenticated(true);
        tokenAndPassAuthentication.setUserDetails(userDetails);
        return tokenAndPassAuthentication;
    }


    @Override// аутентификацию поддерживает (в н.случ. кастомную tokenAndPassAuthentication)
    public boolean supports(Class<?> authClass) {
        return authClass.getName().equals(TokenAndPassAuthentication.class.getName());
    }
}
