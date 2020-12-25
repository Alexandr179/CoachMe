/*
 * 44.Rest_API_Spring_Boot. 19.12.2020, 12:37 / 19.12.2020, 12:38   @A.Alexandr
 * Copyright (c)
 */
package ru.coach.service.security.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.coach.service.security.details.UserDetailsImpl;

import java.util.Collection;

/**
 * в замен стандартной >> Authentication (см.применение в Controller's ..) пишем свою реализацию TokenAndPassAuthentication
 * Теперь! аутентификация идет-> по token's в header REST запросах
 */

public class TokenAndPassAuthentication implements Authentication  {// объект аутентификации User-ра
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private UserDetails userDetails;
    private String token;
    private Boolean isAuthenticated = false;


    public TokenAndPassAuthentication(String token) {
        this.token = token;
    }// ...sett-ры см.ниже

    public TokenAndPassAuthentication() {// нормальная web - аутентификация
        this.token = token;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userDetails.getAuthorities();
    }

    @Override
    public Object getCredentials() {// ??????????????????
        return null;
    }

    @Override
    public Object getDetails() {// ?????????????????? userDetails .......
        return null;
    }

    @Override
    public Object getPrincipal() {
        logger.info("getPrincipal() -> UserDetails: Username {" + userDetails.getUsername() + "}, Password{" + userDetails.getPassword());
        return userDetails;
    }

    @Override
    public boolean isAuthenticated() {
        logger.info("TokenAndPassAuthentication. isAuthenticated(): " + isAuthenticated);
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuth) throws IllegalArgumentException {
        this.isAuthenticated = isAuth;
    }

    /**
     * выбор - тип аутентификации..
     */
    @Override
    public String getName() {// TODO: в web-аутентификации ->  return user.getEmail() (in UserDetailsImpl)
        logger.info("TokenAndPassAuthentication. token is: " + token + "\"");
        if(token != null){// REST auth
            return token;
        } else {
            return userDetails.getUsername();// нормальная web аутентификация. TODO userDetails уже не null !!!!!!!!
        }
    }


    public void setToken(String token) {// set-тер на token
        this.token = token;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }
}
