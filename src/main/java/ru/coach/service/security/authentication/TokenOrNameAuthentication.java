/*
 * 44.Rest_API_Spring_Boot. 19.12.2020, 12:37 / 19.12.2020, 12:38   @A.Alexandr
 * Copyright (c)
 */
package ru.coach.service.security.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import ru.coach.service.security.details.UserDetailsImpl;
import ru.coach.service.security.providers.TokenOrNameAuthProvider;

import java.util.Collection;

public class TokenOrNameAuthentication implements Authentication {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private UserDetailsImpl userDetails;
    private String headerToken = null;
    private Boolean isAuthenticated = false;

    public TokenOrNameAuthentication(String headerToken) {
        this.headerToken = headerToken;
    }
    public TokenOrNameAuthentication() {
    }


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
        if (headerToken != null) {
            return headerToken;
        } else {
//            return userDetails.getUsername();// TODO >>>>>>>>>> ERROR <<<<<<<<<<<<<<<<<<<<<<<<<
            return "admin@gmail.com";
        }
    }


    public void setToken(String headerToken) {
        this.headerToken = headerToken;
    }

    public void setUserDetails(UserDetailsImpl userDetails) {// setter-ed from Provider
        logger.warn("AUTHENTICATION.  setUserDetails() (by PROVIDER): " + userDetails.getUsername());
        this.userDetails = userDetails;
    }
}
