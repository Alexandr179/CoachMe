package ru.coach.service.controllers.restControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.coach.service.dto.UserDto;
import ru.coach.service.models.User;
import ru.coach.service.rerpositories.UserRepository;
import ru.coach.service.security.authentication.TokenAndPassAuthentication;
import ru.coach.service.security.details.UserDetailsImpl;

import java.util.List;

@RestController
public class RestSignInController {

    /**
     * пока user должен пройти аутентификацию в web...
     */

    @GetMapping("/rest/signin")
    public ResponseEntity<UserDto> signInPage(TokenAndPassAuthentication authentication,
                                              @AuthenticationPrincipal UserDetailsImpl userDetails){
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } else {
            return ResponseEntity.ok(UserDto.from(userDetails.getUser()));
        }
    }
}
