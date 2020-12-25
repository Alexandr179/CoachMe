/*
 * 44.Rest_API_Spring_Boot. 19.12.2020, 12:40 / 19.12.2020, 12:40   @A.Alexandr
 * Copyright (c)
 */
package ru.coach.service.security.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import ru.coach.service.security.authentication.TokenAndPassAuthentication;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * так в SpringBoot вылядит фильтр..
 * его задача в REST - вытащить из http объект аутентификации и положить в контекст SecurityContextHolder
 * он (SecurityContextHolder) позволяет работать с   AuthenticationProvider
 * --------------------------------------------------------------------------------------------------------------------
 * Authentication положили в контекст: SecurityContextHolder. чтобы аутентификация была проведена -> вводим Provider
 */

@Component("tokenAuthenticationFilter")
public class TokenAuthenticationFilter extends GenericFilterBean {// создаем свой bean-URL-фильтр по ..GenericFilterBean

    public boolean isRestTokenAuth;
    // чтобы сервер провел аутентификацию ->  указываем провайдер: TokenAuthenticationProvider

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("FILTER: " + servletRequest.toString());
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String headerToken = request.getHeader("headerToken");
        logger.warn("FILTER. token is: \"" + headerToken + "\"");

        Authentication authentication = null;
        if (headerToken != null) {
            isRestTokenAuth = true;
            authentication = new TokenAndPassAuthentication(headerToken);// JwtAuthentication ..
        } else {
            authentication = new TokenAndPassAuthentication();
        }
        // закладываем его в context (для текущего потока). > SecurityContextHolder - контекст, хранит данные по безопасности
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // SecurityContext -> предоставляет Auth провайдеру. Провайдер выставляет Auth !
        // если AuthenticationProvider сделает seAuth = true, то запрос уйдет в Controller
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
