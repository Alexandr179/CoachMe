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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.coach.service.models.User;
import ru.coach.service.rerpositories.UserRepository;
import ru.coach.service.security.authentication.TokenOrNameAuthentication;
import ru.coach.service.security.details.UserDetailsImpl;

import java.util.Optional;

import static ru.coach.service.security.filter.TokenOrNameAuthFilter.isRestTokenAuth;

@Component
public class TokenOrNameAuthProvider implements AuthenticationProvider {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("customUserDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    UserDetailsImpl userDetails;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        TokenOrNameAuthentication tokenOrNameAuthentication = (TokenOrNameAuthentication) authentication;
        Optional<User> userOptionalByRestToken;

        userOptionalByRestToken = userRepository.findByToken(tokenOrNameAuthentication.getName());
        if (userOptionalByRestToken.isPresent() && isRestTokenAuth) {
            userDetails = new UserDetailsImpl(userOptionalByRestToken.get());
            logger.error("PROVIDER. REST auth -> findByToken(): " + userDetails.getUsername());
        } else {
            userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(tokenOrNameAuthentication.getName());
            logger.error("PROVIDER. web auth -> loadUserByUsername(): " + userDetails.getUsername());
        }
        if (userDetails == null) {
            throw new UsernameNotFoundException("PROVIDER >>> User not found");
        }
        tokenOrNameAuthentication.setAuthenticated(true);
        tokenOrNameAuthentication.setUserDetails(userDetails);
        return tokenOrNameAuthentication;
    }

    @Override
    public boolean supports(Class<?> authClass) {
        return authClass.getName().equals(TokenOrNameAuthentication.class.getName());
    }
}
