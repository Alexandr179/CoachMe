package ru.coach.service.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.coach.service.dto.UserDto;
import ru.coach.service.models.User;
import ru.coach.service.rerpositories.UserRepository;
import ru.coach.service.security.authentication.TokenOrNameAuthentication;

import java.util.List;

@RestController
public class RestUsersController {

    @Autowired
    private UserRepository userRepository;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/rest/users")
    public ResponseEntity<List<UserDto>> getUsers(TokenOrNameAuthentication authentication) {
        if (authentication != null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } else {
            List<User> users = userRepository.findAll();
            return ResponseEntity.ok(UserDto.from(users));
        }
    }
}
