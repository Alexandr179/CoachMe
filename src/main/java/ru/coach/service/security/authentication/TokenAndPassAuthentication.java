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

import java.util.Collection;

// ранее в S.Boot мы использовали в Controller-рах :
// public String getUsersPage(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model){.....
//       userDetails ->> AuthUser
// теперь пишем свою реализацию Authentication:

// TODO: ранее User описывался в SpringSecurity помощью UserDetails,
// TODO: сейчас - как объект аутентификации, с пом. TokenAuthentication..

public class TokenAndPassAuthentication implements Authentication  {// объект аутентификации User-ра
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private UserDetails userDetails;
    private String token;
    private Boolean isAuthenticated = false;


    public TokenAndPassAuthentication(String token) {
        this.token = token;
    }// set-теры см.ниже


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userDetails.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        logger.info("getPrincipal() -> UserDetails: Username {" + userDetails.getUsername() + "}, Password{" + userDetails.getPassword());
        return userDetails;
    }

    @Override
    public boolean isAuthenticated() {
        logger.info("isAuthenticated(): " + isAuthenticated);
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuth) throws IllegalArgumentException {
        this.isAuthenticated = isAuth;
    }

    @Override
    public String getName() {
        logger.info("token is: " + token + "\"");
        return token;
    }



    public void setToken(String token) {// set-тер на token
        this.token = token;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }
}
