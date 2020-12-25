/*
 * 44.Rest_API_Spring_Boot. 19.12.2020, 12:37 / 19.12.2020, 12:38   @A.Alexandr
 * Copyright (c)
 */
package ru.coach.service.security.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import ru.coach.service.security.details.UserDetailsImpl;

import java.util.Collection;

/**
 * в замен стандартной >> Authentication (см.применение в Controller's ..) пишем свою реализацию TokenAndPassAuthentication
 * Теперь! аутентификация идет-> по token's в header REST запросах
 */

public class TokenAndPassAuthentication implements Authentication {// объект аутентификации User-ра
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private UserDetailsImpl userDetails;
    private String headerToken;
    private boolean isAuthenticated = false;

    public TokenAndPassAuthentication(String headerToken) {
        this.headerToken = headerToken;
    }
    public TokenAndPassAuthentication() {
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
    public Object getDetails() {// ?????????????????? нужно ли ,,
        return null;
    }

    @Override
    public Object getPrincipal() {
        return userDetails;
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuth) throws IllegalArgumentException {
        this.isAuthenticated = isAuth;
    }

    @Override
    public String getName() {
        logger.warn("AUTHENTICATION. Token is: \"" + headerToken + "\"");
        if (headerToken == null) {
            headerToken = "token";// TODO аутентификация по web -> принудительно выставляем пока нужный token
        }
        return headerToken;
    }


    public void setToken(String headerToken) {// set-тер на token
        logger.warn("AUTHENTICATION. setToken(): \"" + headerToken + "\"");
        if (headerToken == null) {
            headerToken = "token";// TODO аутентификация по web -> принудительно выставляем пока нужный token
        }
        this.headerToken = headerToken;
    }

    public void setUserDetails(UserDetailsImpl userDetails) {// ищем ошибку замена на UserDetailsImpl
        this.userDetails = userDetails;
    }
}
