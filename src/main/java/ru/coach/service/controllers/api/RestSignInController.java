package ru.coach.service.controllers.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.coach.service.dto.UserDto;
import ru.coach.service.models.User;
import ru.coach.service.security.authentication.TokenOrNameAuthentication;
import ru.coach.service.servicesApi.ApiSignInService;

@RestController
public class RestSignInController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ApiSignInService apiSignInService;

    @PostMapping("/rest/signIn")
    public ResponseEntity<UserDto> signInPage(TokenOrNameAuthentication authentication,
                                              @RequestBody UserDto userDto) {
        if (authentication == null) {
            User user = apiSignInService.loadUserByUsername(userDto.getEmail());
            logger.info("REST_SIGN_IN_CONTROLLER, load user: \"" + user.getFirstName() + "\"");
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Authentication", user.getTokens().get(0));
            return ResponseEntity.ok()
                    .headers(responseHeaders)
                    .body(UserDto.from(user));
        } else {
            logger.info("REST_SIGN_IN_CONTROLLER, user has Auth");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }
}
