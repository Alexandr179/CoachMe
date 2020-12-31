/*
 * 44.Rest_API_Spring_Boot. 19.12.2020, 12:40 / 19.12.2020, 12:40   @A.Alexandr
 * Copyright (c)
 */
package ru.coach.service.security.filter;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import ru.coach.service.security.authentication.TokenOrNameAuthentication;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;

@Component("headersTokenAuthenticationFilter")
public class TokenOrNameAuthFilter extends GenericFilterBean {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    public static boolean isRestTokenAuth = false;

    @SneakyThrows
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        logger.warn("FILTER. URI: " + request.getRequestURI());
        Authentication authentication = new TokenOrNameAuthentication();// web auth

        if (isTrackingPath(request.getRequestURI())) {// if... -> rebase on - REST auth
            String restHeaderToken = request.getHeader("Authentication");
            isRestTokenAuth = true;
            if (restHeaderToken != null) {
                logger.warn("FILTER. REST Auth, send TokenOrNameAuthentication(" + restHeaderToken + ") to SecurityContextHolder");
                authentication = new TokenOrNameAuthentication(restHeaderToken);
            } else {
                restHeaderToken = "NO_TOKEN";
                logger.warn("FILTER. REST Auth, send TokenOrNameAuthentication(" + restHeaderToken + ") to SecurityContextHolder");
            }
        }

        logger.warn("FILTER. Authentication: " + authentication.getName());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean isTrackingPath(String path) {
        return path.contains("/rest/");
    }
}

